package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Stefan Olenocin
 * @author Victor Shapo
 */
public class WarehouseView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Warehouse.fxml"));
        primaryStage.setTitle("Warehouse");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.minWidthProperty().setValue(900);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.maxWidthProperty().setValue(900);
        primaryStage.maxHeightProperty().setValue(600);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
