package view;

import controller.OnPathInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;
import model.Path;

public class PathView {

    private final Pane guiPath = new Pane();
    private final Path path;
    private Color pathColor = Color.WHITESMOKE;
    private Color blockedPathColor = Color.INDIANRED;
    private Text informationText;

    public PathView(Coords position, Text informationText) {
        guiPath.getChildren().add(new Rectangle(60,60, pathColor));
        this.guiPath.addEventHandler(MouseEvent.ANY, event -> new OnPathInteraction().handle(event, this));
        this.path = new Path(position);
        this.informationText = informationText;
    }

    public Pane getGuiPath() {
        return guiPath;
    }

    public Path getPath() {
        return path;
    }

    public void updatePathColor(){
        if(getPath().isBlocked()){
            ((Rectangle) getGuiPath().getChildren().get(0)).setFill(blockedPathColor);
        } else {
            ((Rectangle) getGuiPath().getChildren().get(0)).setFill(pathColor);
        }
    }

    public Text getInformationText() {
        return informationText;
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
