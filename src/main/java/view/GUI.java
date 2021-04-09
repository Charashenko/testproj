package view;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.areas.ShelvingArea;
import view.utils.ZoomableScrollPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Stefan Olenocin
 * @author Victor Shapochkin
 */
public class GUI extends Application {

    private static int clock = 1000;
    private static Text informationText = new Text();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent parent = FXMLLoader.load(getClass().getResource("Warehouse.fxml"));
        VBox root = new VBox();
        setupLayout(root);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Warehouse");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().setValue(800);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setupLayout(VBox root) {
        String borderStyle = "-fx-padding: 5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;";

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem importOrderItem = new MenuItem("Import order file");
        importOrderItem.setOnAction(Controller::importOrderFile);
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(Controller::exitApp);
        fileMenu.getItems().addAll(importOrderItem, exitItem);
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

        StackPane informationTextPane = new StackPane();
        informationTextPane.setAlignment(Pos.TOP_LEFT);

        informationText.setTextOrigin(VPos.TOP);
        informationTextPane.getChildren().add(informationText);

        HBox hboxButtons = new HBox();
        Button show = new Button("Show");
        Button hide = new Button("Hide");
        hboxButtons.getChildren().addAll(show, hide);

        gridPane.add(tabPane, 0, 0, 1, 2);
        gridPane.add(informationText, 1, 0, 1, 1);
        gridPane.add(hboxButtons, 1, 1, 1, 1);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        gridPane.getColumnConstraints().addAll(column1, column2);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(90);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        row2.setValignment(VPos.BOTTOM);
        gridPane.getRowConstraints().addAll(row1, row2);
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        HBox hboxClock = new HBox();
        Label hboxClockLabel = new Label(formatter.format(date));
        ProgressIndicator pi = new ProgressIndicator(-1);
        pi.setPrefWidth(20);
        show.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> pi.setVisible(true));
        hide.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> pi.setVisible(false));
        hboxClock.getChildren().addAll(hboxClockLabel, pi);
        hboxClock.setAlignment(Pos.CENTER_LEFT);
        hboxClock.setPrefHeight(10);
        hboxClock.setPrefWidth(1080);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(clock), ae -> updateClock(hboxClockLabel)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.getChildren().addAll(menuBar, gridPane, hboxClock);
    }

    public static Node setupWarehouseTab(Text informationText) {
        Size warehouseSize = new Size(10, 10);
        ZoomableScrollPane zoomablePane = new ZoomableScrollPane();
        WarehouseView warehouseView = new WarehouseView(warehouseSize);
        zoomablePane.addContent(warehouseView.getGuiWarehouse());

        for (int i = 0; i < warehouseSize.getRowCount(); i++) {
            for (int j = 0; j < warehouseSize.getColumnCount(); j++) {
                if (warehouseView.getWarehouse().getWarehouseLayout()[i][j] instanceof ShelvingArea) {
                    Pane svpane = new Pane();
                    ShelfView sv = new ShelfView(new Coords(i, j), informationText);
                    svpane.getChildren().add(sv.getGuiShelf());
                    svpane.setStyle("-fx-border-style: solid outside;" +
                            "-fx-border-width: 2;");
                    sv.getShelfContents().add(new Goods(GoodsType.KLADIVO, 20, 1.5));
                    warehouseView.getGuiWarehouse().add(svpane, j, i);
                } else {
                    Rectangle r = new Rectangle(60, 60, Color.BISQUE);
                    warehouseView.getGuiWarehouse().add(r, j, i);
                }
            }
        }
        warehouseView.getGuiWarehouse().add(new Rectangle(60,60, Color.CRIMSON), 9, 0);
        warehouseView.getGuiWarehouse().autosize();
        return zoomablePane;
    }

    public static Node setupOrdersTab() {
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_LEFT);
        Button b = new Button("Orders");
        b.setPadding(new Insets(2, 10, 2, 10));
        root.getChildren().add(b);
        return root;
    }

    public static List<ShelfView> initialFillUp() {
        return null;
    }

    public static void updateClock(Label cl){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        cl.setText(formatter.format(date));
        clock++;
    }

}
