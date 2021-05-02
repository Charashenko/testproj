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
import model.areas.ShelvingArea;
import view.CartView;
import view.PathView;
import view.ShelfView;
import view.WarehouseView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main MainUI class
 *
 * @author Stefan Olenocin
 * @author Viktor Shapochkin
 */
public class MainUI extends Application {

    private static int clock = 1000;
    private static final Text informationText = new Text();

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

        GridPane gridPane = new GridPane();
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
        tabPane.setPrefHeight(1080);

        VBox informationPanel = new VBox();
        informationPanel.setAlignment(Pos.TOP_CENTER);
        informationPanel.setSpacing(7);

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
        Button configure = new Button("Configure");
        run.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> simulationIndicator.setVisible(true));
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> simulationIndicator.setVisible(false));
        configure.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> new OnConfigureButtonClick().handle(mouseEvent, clockLabel));
        buttonBox.getChildren().addAll(run, configure, stop);

        informationText.setFont(new Font("", 18));
        informationText.setTextAlignment(TextAlignment.CENTER);
        informationPanel.getChildren().addAll(clockBox, buttonBox, informationText);

        gridPane.add(tabPane, 0, 0);
        gridPane.add(informationPanel, 1, 0);
        gridPane.setAlignment(Pos.CENTER);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(80);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        column2.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(5);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(clock), ae -> {
                    if (simulationIndicator.isVisible()) systemUpdate(clockLabel);
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.getChildren().addAll(menuBar, gridPane);
    }

    /**
     * Setup warehouse tab in UI
     *
     * @param informationText Text information area
     * @return Main node of warehouse tab
     */
    public static Node setupWarehouseTab(Text informationText) {
        Size warehouseSize = new Size(10, 10);
        ZoomableScrollPane zoomablePane = new ZoomableScrollPane();
        WarehouseView warehouseView = new WarehouseView(warehouseSize);
        zoomablePane.addContent(warehouseView.getGuiWarehouse());

        for (int row = 0; row < warehouseSize.getRowCount(); row++) {
            for (int col = 0; col < warehouseSize.getColumnCount(); col++) {
                switch (warehouseView.getWarehouse().getWarehouseLayout()[row][col].getType()) {
                    case SHELVING:
                        Pane svpane = new Pane();
                        ShelfView sv = new ShelfView(new Coords(row, col), informationText);
                        svpane.getChildren().add(sv.getGuiShelf());
                        svpane.setStyle("-fx-border-style: solid outside;" +
                                "-fx-border-width: 2;");
                        warehouseView.getGuiWarehouse().add(svpane, col, row);
                        break;
                    case PATH:
                        PathView pv = new PathView(new Coords(row, col), informationText);
                        warehouseView.getGuiWarehouse().add(pv.getGuiPath(), col, row);
                        break;
                    case PARKING:
                        break;
                    case UNLOADING:
                        break;
                    default:
                        Rectangle rectangle = new Rectangle(60, 60, Color.WHITESMOKE);
                        warehouseView.getGuiWarehouse().add(rectangle, col, row);
                }
            }
        }
        CartView cartView = new CartView(new Coords(0, 9), informationText);
        warehouseView.getGuiWarehouse().add(cartView.getGuiCart(),
                cartView.getCart().getPosition().getColumn(), cartView.getCart().getPosition().getRow());

        warehouseView.getGuiWarehouse().autosize();
        return zoomablePane;
    }

    /**
     * Setup orders tab in UI
     *
     * @return Main node of orders tab
     */
    public static Node setupOrdersTab() {
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_LEFT);
        Button b = new Button("Orders");
        b.setPadding(new Insets(2, 10, 2, 10));
        root.getChildren().addAll(b);
        return root;
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
    public static void systemUpdate(Label cl) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        cl.setText(formatter.format(date));
        clock++;
    }

}
