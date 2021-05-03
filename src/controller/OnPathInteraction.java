package controller;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import view.PathView;

/**
 * Path interaction event handler
 */
public class OnPathInteraction {

    /**
     * Handle mouse click event as toggle blocking of path and
     *
     * @param event Mouse click event
     * @param pv    Path view that caused the event
     */
    public void handle(MouseEvent event, PathView pv) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                pv.getPath().setBlockage(!pv.getPath().isBlocked());
                pv.updatePathColor();
            }
        } else if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            pv.getInformationText().setText(String.format("[Blocked path]\n%s", pv.getPath().isBlocked()));
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
            pv.getInformationText().setText("");
        }
    }

}
