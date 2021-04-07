package view;

import controller.Controller;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Coords;
import model.Warehouse;

import java.util.List;

/**
 * @author Stefan Olenocin
 * @author Victor Shapo
 */
public class WarehouseView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent parent = FXMLLoader.load(getClass().getResource("Warehouse.fxml"));
        VBox root = new VBox();
        setupLayout(root);

        Warehouse warehouse = new Warehouse();
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

    public static void setupLayout(VBox root){

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
                    warehouseTab.setContent(setupWarehouseTab());
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
                Text informationText = new Text();
                    informationText.setTextOrigin(VPos.TOP);
                informationTextPane.getChildren().add(informationText);

            HBox hboxButtons = new HBox();
                Button show = new Button("Show");
                Button delete = new Button("Delete");
                hboxButtons.getChildren().addAll(show, delete);

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

        HBox hboxClock = new HBox();
            Label hboxClockLabel = new Label("Clock");
            Pane spacerPane = new Pane();
            spacerPane.setPrefWidth(1080);
            ProgressIndicator pi = new ProgressIndicator(-1);
            pi.setPrefWidth(20);
            hboxClock.getChildren().addAll(hboxClockLabel, spacerPane, pi);
            hboxClock.setAlignment(Pos.CENTER_LEFT);
            hboxClock.setPrefHeight(10);
            hboxClock.setPrefWidth(100);

        root.getChildren().addAll(menuBar, gridPane, hboxClock);
    }

    public static Node setupWarehouseTab(){
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        for (int i = 0; i < 10; i++) {
            ShelfView sv = new ShelfView(new Coords(i, 0));
            sv.getGuiShelf().addEventHandler(MouseEvent.ANY, mouseEvent -> Controller.showShelfContents(mouseEvent, sv));
            vbox.getChildren().add(sv.getGuiShelf());
        }
        return vbox;
    }

    public static Node setupOrdersTab(){
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_LEFT);
        Button b = new Button("Orders");
        b.setPadding(new Insets(2, 10, 2, 10));
        root.getChildren().add(b);
        return root;
    }

    public static List<ShelfView> initialFillUp(){
        return null;
    }
}
