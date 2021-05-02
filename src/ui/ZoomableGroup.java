package ui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

/**
 * Zoomable group
 */
public class ZoomableGroup extends Group {

    private final double scale = 1.1;

    public ZoomableGroup() {
        this.setAutoSizeChildren(true);
        this.addEventHandler(ScrollEvent.SCROLL, this::onScroll);
    }

    /**
     * Handle scroll event
     * @param event Scroll event
     */
    public void onScroll(ScrollEvent event){
        event.consume();
        Node node = this.getChildren().get(0);
        if(event.getDeltaY()>0){ // zoom in
            node.setScaleX(node.getScaleX()*scale*scale);
            node.setScaleY(node.getScaleY()*scale*scale);
        } else { // zoom out
            node.setScaleX(node.getScaleX()*1/scale);
            node.setScaleY(node.getScaleY()*1/scale);
        }
    }

    public void addContent(Node node) {
        this.getChildren().add(node);
    }
}
