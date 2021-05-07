package ui;

import controller.OnExitApp;
import controller.OnImportOrderFile;
import controller.OnShowAppAbout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;
import view.CartView;
import view.WarehouseView;

import java.util.ArrayList;
import java.util.Arrays;

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

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(7);
        buttonBox.setAlignment(Pos.CENTER);
        ProgressIndicator simulationIndicator = new ProgressIndicator(-1);
        simulationIndicator.setPrefSize(20, 20);
        simulationIndicator.setVisible(false);
        Button runButton = new Button("Run");
        Button stopButton = new Button("Stop");
        stopButton.setDisable(true);
        Button resetButton = new Button("Reset");
        Button configureButton = new Button("Configure");

        runButton.setOnAction(actionEvent -> {
            simulationIndicator.setVisible(true);
            runButton.setDisable(true);
            stopButton.setDisable(false);
            resetButton.setDisable(false);
            clock.setRunning(true);
            systemUpdate();
        });

        stopButton.setOnAction(mouseEvent -> {
            simulationIndicator.setVisible(false);
            stopButton.setDisable(true);
            runButton.setDisable(false);
            clock.setRunning(false);
        });

        resetButton.setOnAction(mouseEvent -> {
            simulationIndicator.setVisible(false);
            resetButton.setDisable(true);
            runButton.setDisable(false);
            stopButton.setDisable(true);
            clock.setRunning(false);
            warehouseView.createDefaultUnitViews();
            //warehouseView.setUnitViews(new ArrayList<>(startPoint.getUnitViews())); //TODO Opravit do buducna... ()
            warehouseView.drawGui();
        });

        buttonBox.getChildren().addAll(runButton, stopButton, configureButton, resetButton, simulationIndicator);

        VBox configureBox = new VBox();
        HBox currentBox = new HBox();
        Label currentLabel = new Label("Current clock");
        Label currentClock = new Label(String.valueOf(clock.getClock()));
        currentBox.setSpacing(10);
        currentBox.setAlignment(Pos.CENTER);
        currentBox.getChildren().addAll(currentLabel, currentClock);

        HBox newBox = new HBox();
        Label newLabel = new Label("New clock");
        TextField newClock = new TextField();
        newClock.setMaxWidth(75);
        newBox.setSpacing(5);
        newBox.setAlignment(Pos.CENTER);
        newBox.getChildren().addAll(newLabel, newClock);
        configureBox.getChildren().addAll(currentBox, newBox);

        configureButton.setOnAction(mouseEvent -> {
            try {
                clock.setClock(Integer.parseInt(newClock.getText()));
                currentClock.setText(newClock.getText());
            } catch (NumberFormatException e){
                mouseEvent.consume();
            }
        });

        informationText.setFont(new Font("", 18));
        informationText.setTextAlignment(TextAlignment.CENTER);
        informationPanel.getChildren().addAll(buttonBox, configureBox, informationText);
        informationPanel.setMinWidth(350);
        informationPanel.setPadding(new Insets(20,2,2,2));
        informationPanel.setAlignment(Pos.TOP_CENTER);
        informationPanel.setSpacing(7);

        ordersTab.setOnSelectionChanged(event -> {
            runButton.setVisible(!runButton.isVisible());
            stopButton.setVisible(!stopButton.isVisible());
            configureButton.setVisible(!configureButton.isVisible());
            resetButton.setVisible(!resetButton.isVisible());
            configureBox.setVisible(!configureBox.isVisible());
        });

        mainSection.getChildren().add(tabPane);
        mainSection.getChildren().add(informationPanel);

        root.getChildren().addAll(menuBar, mainSection);
    }

    /**
     * Setup warehouse tab in UI
     *
     * @param informationText Text information area
     * @return Main node of warehouse tab
     */
    public static Node setupWarehouseTab(Text informationText) {
        ZoomableScrollPane zoomablePane = new ZoomableScrollPane();
        zoomablePane.setPrefSize(1920, 1080);
        warehouseView = new WarehouseView(informationText);
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
        VBox ordersVBox = new VBox();
        ordersVBox.setPrefSize(1920,1080);
        ordersVBox.setSpacing(5);
        HBox ordersControl = new HBox();
        ordersControl.setPadding(new Insets(20,0,10,20));
        ordersControl.setSpacing(5);
        ChoiceBox<GoodsType> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Arrays.asList(GoodsType.values()));
        Button plus = new Button("+");
        Button minus = new Button("-");
        plus.setDisable(true);
        minus.setDisable(true);
        Label countLabel = new Label("1");
        countLabel.setFont(new Font(20));
        Slider slider = new Slider();
        slider.setMax(24);
        slider.setMin(1);
        slider.setMajorTickUnit(4);
        slider.setBlockIncrement(1);
        slider.setShowTickMarks(true);
        slider.setPrefWidth(300);
        slider.setDisable(true);

        slider.valueProperty().addListener(observable -> {
            countLabel.setText(String.valueOf((int) slider.getValue()));
        });

        plus.setOnAction(actionEvent -> {
            countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText())+1));
            slider.setValue(Integer.parseInt(countLabel.getText()));
        });
        minus.setOnAction(actionEvent -> {
            if(Integer.parseInt(countLabel.getText())-1 >= 1){
                countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText())-1));
                slider.setValue(Integer.parseInt(countLabel.getText()));
            }
        });
        ordersControl.getChildren().addAll(choiceBox, plus, minus, slider, countLabel);
        HBox orderManipulationButtons = new HBox();
        orderManipulationButtons.setSpacing(10);
        orderManipulationButtons.setPadding(new Insets(0,0,10,20));
        Button addButton = new Button("Add to order");
        Button removeButton = new Button("Remove from order");
        Button confirmButton = new Button("Confirm current order");
        Button deleteButton = new Button("Delete current order");
        addButton.setDisable(true);
        removeButton.setDisable(true);
        confirmButton.setDisable(true);
        deleteButton.setDisable(true);

        choiceBox.setOnAction(actionEvent -> {
            plus.setDisable(false);
            minus.setDisable(false);
            addButton.setDisable(false);
            removeButton.setDisable(false);
            confirmButton.setDisable(false);
            deleteButton.setDisable(false);
            slider.setDisable(false);
        });

        orderManipulationButtons.getChildren().addAll(addButton, removeButton, confirmButton, deleteButton);

        VBox currentOrderTextBox = new VBox();
        currentOrderTextBox.setPadding(new Insets(0,0,10,20));
        Label currentOrderLabel = new Label("Current order:");
        currentOrderLabel.setFont(new Font(20));
        Text currentOrder = new Text();
        currentOrder.setFont(new Font(16));

        Order order = new Order();
        addButton.setOnAction(actionEvent -> {
            order.addGoodsToOrder(choiceBox.getValue(), Integer.parseInt(countLabel.getText()));
            currentOrder.setText(order.getOrderItems());
        });
        removeButton.setOnAction(actionEvent -> {
            order.removeGoodsFromOrder(choiceBox.getValue(), Integer.parseInt(countLabel.getText()));
            currentOrder.setText(order.getOrderItems());
        });
        confirmButton.setOnAction(actionEvent -> {
            System.out.println("order confirmed");
        });
        deleteButton.setOnAction(actionEvent -> {
            order.clearOrder();
            currentOrder.setText(order.getOrderItems());
        });

        currentOrderTextBox.getChildren().addAll(currentOrderLabel, currentOrder);
        ordersVBox.getChildren().addAll(ordersControl, orderManipulationButtons, currentOrderTextBox);
        return ordersVBox;
    }

    /**
     * Updates system every specified time and calculates routes of carts
     */
    public static void systemUpdate() {
        new Thread(() -> { //cart movement thread
            try {
                while (true) {
                    for (CartView cv : warehouseView.getCartViews()) {
                        cv.getCart().nextStep(clock.getClock() - clock.getClock() / 10, warehouseView);
                    }
                    Thread.sleep(clock.getClock());
                    if(!clock.isRunning()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        new Thread(() -> { //pathfinding
//            try {
//                Pathfinder pathfinder = new Pathfinder(warehouseView);
//                while (true) {
//                    for (CartView cv : warehouseView.getCartViews()) {
//
//                    }
//                    Thread.sleep(clock.getClock());
//                    if(!clock.isRunning()) break;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    public static WarehouseView getWarehouseView() {
        return warehouseView;
    }
}
