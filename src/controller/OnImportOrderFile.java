package controller;

import javafx.event.Event;

/**
 * Import order button event handler.
 */
public class OnImportOrderFile {

    /**
     * Import file with predefined order
     * @param event Mouse click event
     */
    public void handle(Event event) {
        event.consume();
        System.out.println("Importing order file...");
        //TODO Importing of file with orders
    }
}
