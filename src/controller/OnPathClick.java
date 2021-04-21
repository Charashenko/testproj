package controller;

import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            pv.getPath().setBlockage(!pv.getPath().isBlocked());
            pv.updatePathColor();
        }
    }

}
