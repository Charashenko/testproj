package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Goods;
import view.ShelfView;

import java.util.HashMap;

public class Controller {

    /**
     * Exit application on click
     * @param event Mouse click event
     */
    public static void exitApp(ActionEvent event) {
        System.exit(1);
    }

    public static void showAboutInfo(ActionEvent event) {
        //TODO About popup window
    }

    public static void importOrderFile(ActionEvent event) {
        System.out.println("Importing order file...");
        //TODO Importing of file with orders
    }

    /**
     * Show clicked shelf contents and change color, when mouse is hovering above shelf
     * @param event Mouse event
     * @param sv Shelf that caused the event
     */
    public static void showShelfContents(MouseEvent event, ShelfView sv) {
        Rectangle r = (Rectangle) event.getTarget();
        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            r.setFill(Color.GOLDENROD);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(Color.DODGERBLUE);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            //TODO Nicer format
            HashMap<Goods, Integer> numberOfSameGoods = new HashMap<>();
            for (Goods g : sv.getShelfContents()) {
                numberOfSameGoods.putIfAbsent(g, 1);
                int count = numberOfSameGoods.get(g);
                numberOfSameGoods.put(g, ++count);
            }
            String output = String.format("[Shelf contents]%n");
            for (Goods g : numberOfSameGoods.keySet()) {
                output = String.format("%s%s [Price] %s [Weight] %s%n", output, g.getGoodsType().name(), g.getPrice(), g.getWeight());
            }
            sv.getInformationText().setText(output);
        }
        event.consume();
    }
}
