package view.utils;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ZoomableScrollPane extends ScrollPane {

    private ZoomableGroup zoomableGroup = new ZoomableGroup();

    public ZoomableScrollPane() {
        this.setPannable(true);
        this.setContent(zoomableGroup);
    }

    public void addContent(Node node){
        this.zoomableGroup.addContent(node);
    }
}
