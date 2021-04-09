package controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Goods;
import model.GoodsType;
import view.CartView;

import javax.annotation.processing.Completion;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 */
public class OnCartInteraction {

    /**
     * Show cart contents and path. Change color and show cart's path, when mouse is hovering above it
     * @param event Mouse event
     * @param cv Cart that caused the event
     */
    public void handle(MouseEvent event, CartView cv){
        event.consume();
        Rectangle r = (Rectangle) event.getTarget();
        //TODO Nicer format
        String output = String.format("[Cart contents]%n");
        if (cv.getCart().getTransportedGoods() != null) {
            HashMap<Goods, Integer> numberOfSameGoods = new HashMap<>();
            for (Goods g : cv.getCart().getTransportedGoods()) {
                numberOfSameGoods.putIfAbsent(g, 1);
                int count = numberOfSameGoods.get(g);
                numberOfSameGoods.put(g, ++count);
            }
            for (Goods g : numberOfSameGoods.keySet()) {
                output = String.format("%s%s [Price] %s [Weight] %s%n", output, g.getGoodsType().name(), g.getPrice(), g.getWeight());
            }
        }

        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            r.setFill(Color.GOLDENROD);
            cv.getInformationText().setText(output);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(Color.CRIMSON);
            cv.getInformationText().setText("");
        } else if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            r.setFill(Color.GOLDENROD);
            cv.getInformationText().setText(output);
        }

    }

}
