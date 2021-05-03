package ui.hw2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Coords;
import model.Goods;
import model.GoodsType;
import view.CartView;
import view.ShelfView;

public class TestShapochkin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Text informationtext = new Text();
        Label infoAboutTest = new Label("Ukazka zobrazeni policek, voziku a jejich obsahu");
        infoAboutTest.setPadding(new Insets(3));
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.CENTER);
        gridPane.setPadding(new Insets(5));
        gridPane.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 2;");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ShelfView shelfView = new ShelfView(new Coords(i, j), informationtext);
                shelfView.getShelf().addGoods(new Goods(GoodsType.LAMPA, i, j));
                gridPane.add(shelfView.getGuiShelf(), j, i);
            }
        }
        CartView cartView = new CartView(new Coords(0, 2), informationtext);
        gridPane.add(cartView.getGuiCart(), cartView.getUnitPosition().getColumn(),
                cartView.getUnitPosition().getRow());

        for (int i = 0; i < gridPane.getColumnCount(); i++) {
            gridPane.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            gridPane.getRowConstraints().add(rc);
        }
        root.getChildren().addAll(infoAboutTest, gridPane, informationtext);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Shapochkin, Homework test class");
        stage.show();
    }

}
