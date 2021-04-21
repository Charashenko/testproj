package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Configure clock button event handler
 */
public class OnConfigureButtonClick {

    /**
     * Handle mouse click event
     * @param event Mouse click event
     */
    public void handle(MouseEvent event){
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Configure clock");
        stage.show();
    }

}
