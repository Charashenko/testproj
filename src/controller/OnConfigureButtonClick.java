package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Configure clock button event handler
 */
public class OnConfigureButtonClick {

    /**
     * Handle mouse click event
     * @param event Mouse click event
     */
    public void handle(MouseEvent event, Label hboxClock){
        event.consume();
        StackPane root = new StackPane();

        Label clock = new Label(hboxClock.getText());
        root.getChildren().addAll(clock);

        Scene scene = new Scene(root, 300, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Configure clock");
        stage.show();
    }

}
