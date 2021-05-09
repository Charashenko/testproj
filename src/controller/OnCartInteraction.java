package controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Coords;
import model.Direction;
import model.Goods;
import ui.MainUI;
import view.CartView;
import view.PathView;

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
                    } else {
                        numberOfSameGoods.put(g, 1);
                    }
                }
                for (Goods g : numberOfSameGoods.keySet()) {
                    output = String.format("%s%dx %s [Price] %s%n", output, numberOfSameGoods.get(g),
                            g.getGoodsType().name(), g.getPrice());
                }
            }
            r.setFill(Color.GOLDENROD);
            cv.getInformationText().setText(output);
            Coords currentCoords = cv.getCart().getHomePosition();
            for(Direction d: cv.getCart().getPlannedRoute().getPlannedPath().values()) {
                switch (d) {
                    case UP:
                        currentCoords.decrementRow();
                        break;
                    case DOWN:
                        currentCoords.incrementRow();
                        break;
                    case LEFT:
                        currentCoords.decrementColumn();
                        break;
                    case RIGHT:
                        currentCoords.incrementColumn();
                        break;
                    case TAKEOUT:
                        break;
                }
                PathView pathView = MainUI.getWarehouseView().getPathViewAtCoords(currentCoords);
                if (pathView != null) {
                    pathView.setShowingCartRoute(true);
                    pathView.updatePathColor();
                }
            }
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            r.setFill(cv.getCartColor());
            cv.getInformationText().setText("");
            Coords currentCoords = cv.getCart().getHomePosition();
            for(Direction d: cv.getCart().getPlannedRoute().getPlannedPath().values()) {
                switch (d) {
                    case UP:
                        currentCoords.decrementRow();
                        break;
                    case DOWN:
                        currentCoords.incrementRow();
                        break;
                    case LEFT:
                        currentCoords.decrementColumn();
                        break;
                    case RIGHT:
                        currentCoords.incrementColumn();
                        break;
                    case TAKEOUT:
                        break;
                }
                PathView pathView = MainUI.getWarehouseView().getPathViewAtCoords(currentCoords);
                if (pathView != null) {
                    pathView.setShowingCartRoute(false);
                    pathView.updatePathColor();
                }
            }
        }
    }

}
