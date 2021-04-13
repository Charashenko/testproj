package ui.hw2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.ZoomableScrollPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestOlenocin extends Application {

    private final int clock = 1000;
    private int counter = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        Label infoAboutTest = new Label("Ukazka zoomovania, posuvania, spustania a pauznutia \n" +
                "simulacie s pevne danym casovym intervalom");
        infoAboutTest.setPadding(new Insets(3));
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane();
        zoomableScrollPane.setPadding(new Insets(5));
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                gridPane.add(new Rectangle(60,60, Color.rgb(new Random().nextInt(255),
                        new Random().nextInt(255), new Random().nextInt(255))), j, i);
            }
        }
        zoomableScrollPane.addContent(gridPane);

        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        HBox clockTray = new HBox();
        clockTray.setSpacing(5);
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Label clockLabel = new Label(String.valueOf(counter));
        Label clockInterval = new Label("Speed " + clock + " ms");
        ProgressIndicator pi = new ProgressIndicator(-1);
        pi.setPrefWidth(20);
        pi.setVisible(false);
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> pi.setVisible(true));
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> pi.setVisible(false));
        clockTray.getChildren().addAll(clockInterval, start, stop, clockLabel, pi);
        clockTray.setAlignment(Pos.CENTER_LEFT);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(clock), ae -> {if(pi.isVisible()) systemUpdate(clockLabel);}));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        root.getChildren().addAll(infoAboutTest, zoomableScrollPane, clockTray);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Olenocin, Homework test class");
        stage.show();
    }

    private void systemUpdate(Label lbl){
        lbl.setText(String.valueOf(++counter));
    }
}
