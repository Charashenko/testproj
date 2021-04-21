package view;

import controller.OnPathClick;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Coords;
import model.Path;

public class PathView {

    private Rectangle guiPath;
    private Path path;
    private Color pathColor = Color.LIGHTGRAY;
    private Color blockedPathColor = Color.INDIANRED;

    public PathView(Coords position) {
        this.guiPath = new Rectangle(60,60, pathColor);
        this.guiPath.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new OnPathClick().handle(event, this));
        this.path = new Path(position);
    }

    public Rectangle getGuiPath() {
        return guiPath;
    }

    public Path getPath() {
        return path;
    }

    public void updatePathColor(){
        if(getPath().isBlocked()){
            getGuiPath().setFill(blockedPathColor);
        } else {
            getGuiPath().setFill(pathColor);
        }
    }

    public Color getPathColor() {
        return pathColor;
    }

    public void setPathColor(Color pathColor) {
        this.pathColor = pathColor;
    }

    public Color getBlockedPathColor() {
        return blockedPathColor;
    }

    public void setBlockedPathColor(Color blockedPathColor) {
        this.blockedPathColor = blockedPathColor;
    }
}
