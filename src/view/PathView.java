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

    public PathView(Coords position) {
        this.guiPath = new Rectangle(60,60, Color.TAN);
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
            getGuiPath().setFill(Color.INDIANRED);
        } else {
            getGuiPath().setFill(Color.TAN);
        }
    }
}
