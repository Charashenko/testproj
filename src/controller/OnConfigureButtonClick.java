package controller;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Clock;

import java.util.InputMismatchException;

/**
 * Configure clock button event handler
 */
public class OnConfigureButtonClick {

    /**
     * Handle mouse click event
     * @param event Mouse click event
     */
    public void handle(ActionEvent event, Clock clock){
        event.consume();
        VBox root = new VBox();

        Label currentClock = new Label(String.valueOf(clock.getClock()));
        Button confirm = new Button("Confirm");
        TextField newClock = new TextField();
        confirm.setOnAction(actionEvent -> {
            try{
            currentClock.setText(newClock.getText());
            clock.setClock(Integer.parseInt(newClock.getText()));
            clock.setHasChanged(true);
            }
            catch (InputMismatchException e){
                e.printStackTrace();
            }
        });
        root.getChildren().addAll(currentClock, confirm, newClock);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Configure clock");
        stage.show();
    }
}
