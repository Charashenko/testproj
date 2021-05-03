package controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Goods;
import view.ShelfView;

import java.util.HashMap;

/**
 * Shelf interaction event handler
 */
public class OnShelfInteraction {

    /**
     * Show shelf contents and change color, when mouse is hovering above it
     * @param event Mouse event
     * @param sv Shelf that caused the event
     */
    public void handle(MouseEvent event, ShelfView sv){
        Rectangle r = (Rectangle) event.getTarget();

        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            //TODO Nicer format
            HashMap<Goods, Integer> numberOfSameGoods = new HashMap<>();
            for (Goods g : sv.getShelfContents()) {
                if(numberOfSameGoods.containsKey(g)){
                    int count = numberOfSameGoods.get(g);
                    numberOfSameGoods.put(g, ++count);
                }else {
                    numberOfSameGoods.put(g, 1);
                }
            }
            String output = String.format("[Shelf contents]%n");
            for (Goods g : numberOfSameGoods.keySet()) {
                output = String.format("%s%dx %s [Price] %s [Weight] %s%n", output, numberOfSameGoods.get(g),
                        g.getGoodsType().name(), g.getPrice(), g.getWeight());
            }
            r.setFill(Color.GOLDENROD);
            sv.getInformationText().setText(output);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(sv.getShelfColor());
            sv.getInformationText().setText("");
        }
    }
}
