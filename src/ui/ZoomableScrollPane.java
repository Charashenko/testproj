package ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * Zoomable and pannable pane
 */
public class ZoomableScrollPane extends ScrollPane {

    private final ZoomableGroup zoomableGroup = new ZoomableGroup();

    public ZoomableScrollPane() {
        this.setPannable(true);
        this.setContent(zoomableGroup);
    }

    /**
     * Add node to be inside pane and be zoomable
     * @param node Node to be added.
     */
    public void addContent(Node node){
        this.zoomableGroup.addContent(node);
    }
}
