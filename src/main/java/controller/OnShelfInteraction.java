package controller;

import javafx.event.Event;
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
        event.consume();
        Rectangle r = (Rectangle) event.getTarget();
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

        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            r.setFill(Color.GOLDENROD);
            sv.getInformationText().setText(output);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(Color.DODGERBLUE);
            sv.getInformationText().setText("");
        }
    }
}
