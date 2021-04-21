package controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Goods;
import view.CartView;

import java.util.HashMap;

/**
 * Cart interaction event handler
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
        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            //TODO Nicer format
            String output = String.format("[Cart contents]%n");
            if (cv.getCart().getTransportedGoods() != null) {
                HashMap<Goods, Integer> numberOfSameGoods = new HashMap<>();
                for (Goods g : cv.getCart().getTransportedGoods()) {
                    if(numberOfSameGoods.containsKey(g)){
                        int count = numberOfSameGoods.get(g);
                        numberOfSameGoods.put(g, ++count);
                    }else {
                        numberOfSameGoods.put(g, 1);
                    }
                }
                for (Goods g : numberOfSameGoods.keySet()) {
                    output = String.format("%s%dx %s [Price] %s [Weight] %s%n", output, numberOfSameGoods.get(g),
                            g.getGoodsType().name(), g.getPrice(), g.getWeight());
                }
            }
            r.setFill(Color.GOLDENROD);
            cv.getInformationText().setText(output);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(Color.FORESTGREEN);
            cv.getInformationText().setText("");
        }
    }

}
