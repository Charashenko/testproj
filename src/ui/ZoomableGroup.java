package ui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Zoomable group
 */
public class ZoomableGroup extends Group {

    private final double scale = 1.1;

    public ZoomableGroup() {
        this.setAutoSizeChildren(true);
        this.addEventHandler(ScrollEvent.SCROLL, this::onScroll);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::cancelEvent);
    }

    /**
     * Handle scroll event
     * @param event Scroll event
     */
    public void onScroll(ScrollEvent event){
        Node node = this.getChildren().get(0);
        if(event.getDeltaY()>0){ // zoom in
            if(node.getScaleX() < 1.7) {
                node.setScaleX(node.getScaleX() * scale * scale);
                node.setScaleY(node.getScaleY() * scale * scale);
            }
        } else if(event.getDeltaY()<0) { // zoom out
            if(node.getScaleX() > 0.3) {
                node.setScaleX(node.getScaleX() * 1 / scale);
                node.setScaleY(node.getScaleY() * 1 / scale);
            }
        }
    }

    /**
     * Cancel mouse drag events if neither middle or secondary mouse button is pressed
     * @param event Mouse event
     */
    public void cancelEvent(MouseEvent event){
        if(!(event.isSecondaryButtonDown() || event.isMiddleButtonDown()))
            event.consume();
    }

    /**
     * Add node to be in zoomable and pannable pane
     * @param node Node to be added
     */
    public void addContent(Node node) {
        this.getChildren().add(node);
    }
}
