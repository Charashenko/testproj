package controller;

import javafx.event.Event;
import model.Order;
import view.WarehouseView;

/**
 * Import order button event handler.
 */
public class OnImportOrderFile {

    /**
     * Import file with predefined order
     * @param event Mouse click event
     */
    public void handle(Event event, WarehouseView warehouseView, Order order) {
        event.consume();
        order = warehouseView.parseOrdersYaml().get(0);
        System.out.println("Importing order file...");
        //TODO Importing of file with orders
    }
}
