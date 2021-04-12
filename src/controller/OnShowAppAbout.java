package controller;

import javafx.event.Event;

/**
 * About application button event handler.
 */
public class OnShowAppAbout {

    /**
     * Show application about information.
     * @param event Mouse click event
     */
    public void handle(Event event) {
        event.consume();
        System.out.println("Showing app about window...");
        //TODO Show popup about window
    }
}
