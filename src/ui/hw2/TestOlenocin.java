package ui.hw2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.ZoomableScrollPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestOlenocin extends Application {

    private final int clock = 500;
    private int counter = 0;
    private boolean state = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane();
        zoomableScrollPane.setPadding(new Insets(5));

        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.autosize();
        Rectangle r = new Rectangle(100,100);
        pane.getChildren().add(r);
        zoomableScrollPane.addContent(pane);

        Button b1 = new Button("Button");
        b1.setOnAction(actionEvent -> state=!state);
        root.getChildren().addAll(zoomableScrollPane, b1);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(clock), ae -> {
                    if(state) systemUpdate(r);
                    else systemUpdate2(r);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Olenocin, Homework test class");
        stage.show();
    }

    private void systemUpdate(Rectangle r){
        System.out.println("pressed");
        r.setTranslateX(r.getTranslateX()+10);
    }

    private void systemUpdate2(Rectangle r){
        System.out.println("unpressed");
        r.setTranslateX(r.getTranslateX()-10);
    }
}
