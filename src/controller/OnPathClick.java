package controller;

import javafx.scene.input.MouseEvent;
import view.PathView;

/**
 * Path click event handler
 */
public class OnPathClick {

    /**
     * Handle mouse click event
     * @param event Mouse click event
     * @param pv Path that caused the event
     */
    public void handle(MouseEvent event, PathView pv){
        pv.getPath().setBlockage(!pv.getPath().isBlocked());
        pv.updatePathColor();
    }

}
