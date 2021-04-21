package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OnConfigureButtonClick {

    public void handle(MouseEvent event){
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Configure clock");
        stage.show();
    }

}
