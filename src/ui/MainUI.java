package ui;

import controller.OnConfigureButtonClick;
import controller.OnExitApp;
import controller.OnImportOrderFile;
import controller.OnShowAppAbout;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.areas.AreaType;
import model.areas.ShelvingArea;
import view.CartView;
import view.PathView;
import view.ShelfView;
import view.WarehouseView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Main MainUI class
 *
 * @author Stefan Olenocin
 * @author Viktor Shapochkin
 */
public class MainUI extends Application {

    private static Clock clock = new Clock(1000);
    private static final Text informationText = new Text();
    private static WarehouseView warehouseView;
    private static StartPoint startPoint;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        setupLayout(root);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Warehouse");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().setValue(1000);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.setOnCloseRequest(windowEvent -> Platform.exit());
        primaryStage.show();
    }

    /**
     * Setup main UI layout
     *
     * @param root Root node
     */
    public static void setupLayout(VBox root) {
        String borderStyle = "-fx-padding: 5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 2;" +
                "-fx-border-color: black;";

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem importOrderItem = new MenuItem("Import order file");
        importOrderItem.setOnAction(actionEvent -> new OnImportOrderFile().handle(actionEvent));
        MenuItem showAppAboutItem = new MenuItem("About");
        showAppAboutItem.setOnAction(actionEvent -> new OnShowAppAbout().handle(actionEvent));
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(actionEvent -> new OnExitApp().handle(actionEvent));
        fileMenu.getItems().addAll(importOrderItem, showAppAboutItem, exitItem);
        menuBar.getMenus().addAll(fileMenu);

        HBox mainSection = new HBox();
        TabPane tabPane = new TabPane();
        Tab warehouseTab = new Tab("Warehouse");
        warehouseTab.setContent(setupWarehouseTab(informationText));
        warehouseTab.setClosable(false);
        warehouseTab.getContent().setStyle(borderStyle);
        Tab ordersTab = new Tab("Orders");
        ordersTab.setContent(setupOrdersTab());
        ordersTab.setClosable(false);
        ordersTab.getContent().setStyle(borderStyle);
        tabPane.getTabs().addAll(warehouseTab, ordersTab);

        VBox informationPanel = new VBox();

        HBox clockBox = new HBox();
        clockBox.setSpacing(7);
        clockBox.setAlignment(Pos.CENTER);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        Label clockLabel = new Label(formatter.format(date));
        clockLabel.setFont(new Font("", 20));
        ProgressIndicator simulationIndicator = new ProgressIndicator(-1);
        simulationIndicator.setPrefSize(20, 20);
        simulationIndicator.setVisible(false);
        clockBox.getChildren().addAll(clockLabel, simulationIndicator);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(7);
        buttonBox.setAlignment(Pos.CENTER);
        Button run = new Button("Run");
        Button stop = new Button("Stop");
        stop.setDisable(true);
        Button reset = new Button("Reset");
        reset.setDisable(true);
        Button configure = new Button("Configure");
        //Thread clockThread = new Thread(() -> systemUpdate(clockLabel));

        run.setOnAction(actionEvent -> {
            simulationIndicator.setVisible(true);
            run.setDisable(true);
            stop.setDisable(false);
            reset.setDisable(false);
            //clockThread.start();
        });
        stop.setOnAction( mouseEvent -> {
            simulationIndicator.setVisible(false);
            stop.setDisable(true);
            run.setDisable(false);
            //clockThread.interrupt();
        });
        configure.setOnAction(
                mouseEvent -> new OnConfigureButtonClick().handle(mouseEvent, clockLabel, clock));
        reset.setOnAction(mouseEvent -> {
            reset.setDisable(true);
//            warehouseView.setUnitViews(new ArrayList<>(startPoint.getUnitViews()));
            warehouseView.createDefaultUnitViews();
            warehouseView.drawGui();
        });
        buttonBox.getChildren().addAll(run, stop, configure, reset);

        informationText.setFont(new Font("", 18));
        informationText.setTextAlignment(TextAlignment.CENTER);
        informationPanel.getChildren().addAll(clockBox, buttonBox, informationText);
        informationPanel.setMinWidth(350);
        informationPanel.setAlignment(Pos.TOP_CENTER);
        informationPanel.setSpacing(7);

        mainSection.getChildren().add(tabPane);
        mainSection.getChildren().add(informationPanel);

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(clock.getClock()), ae -> {
            if (simulationIndicator.isVisible()){
                systemUpdate(clockLabel);
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.getChildren().addAll(menuBar, mainSection);
    }

    /**
     * Setup warehouse tab in UI
     *
     * @param informationText Text information area
     * @return Main node of warehouse tab
     */
    public static Node setupWarehouseTab(Text informationText) {
        Size warehouseSize = new Size(22, 22); //default
        ZoomableScrollPane zoomablePane = new ZoomableScrollPane();
        zoomablePane.setPrefSize(1920, 1080);
        warehouseView = new WarehouseView(warehouseSize, informationText);
        startPoint = new StartPoint(new ArrayList<>(warehouseView.getUnitViews()));
        zoomablePane.addContent(warehouseView.getGuiWarehouse());
        return zoomablePane;
    }

    /**
     * Setup orders tab in UI
     *
     * @return Main node of orders tab
     */
    public static Node setupOrdersTab() {
        return new Button("Orders");
    }

    /**
     * Initial fill up of warehouse shelves
     *
     * @param warehouseView Warehouse view object
     */
    public static void initialFillUp(WarehouseView warehouseView) {

    }

    /**
     * Updates system on passed time. For testing purposes updates label every second with current time
     *
     * @param cl Label to be updated
     */
    public static void systemUpdate(Label cl){
        cl.setText(String.valueOf(clock.getClock()));
        for (CartView cv : warehouseView.getCartViews()) {
            cv.getCart().nextStep(clock.getClock() - clock.getClock() / 10, warehouseView);
        }
//        try {
//            while (!Thread.currentThread().isInterrupted()) {
//
//                Thread.sleep(clock.getClock());
//            }
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }

}
