package controller;

import javafx.event.Event;

/**
 * File menu exit button event handler.
 */
public class OnExitApp {

    /**
     * Exit application on click.
     * @param event Mouse click event.
     */
    public void handle(Event event) {
        System.exit(1);
    }
}
