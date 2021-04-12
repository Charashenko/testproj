package ui.hw2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.ZoomableScrollPane;

import java.util.Random;

public class TestOlenocin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Label infoAboutTest = new Label("Ukazka zoomovania, posuvania, spustania a stop simulacie");
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
        root.getChildren().addAll(infoAboutTest, zoomableScrollPane);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Olenocin, Homework test class");
        stage.show();
    }
}
