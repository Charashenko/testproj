package view;

import controller.OnPathInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;
import model.Path;

public class PathView implements UnitView {

    private final Pane guiPath = new Pane();
    private final Path path;
    private final Color pathColor = Color.INDIANRED;
    private final Color blockedPathColor = Color.WHITESMOKE;
    private final Text informationText;
    private final UnitTypes unitType = UnitTypes.PATHVIEW;
    private Coords unitPosition;

    public PathView(Coords position, Text informationText) {
        guiPath.getChildren().add(new Rectangle(60,60, pathColor));
        this.guiPath.addEventHandler(MouseEvent.ANY, event -> new OnPathInteraction().handle(event, this));
        this.path = new Path();
        this.informationText = informationText;
        this.unitPosition = position;
    }

    @Override
    public UnitTypes getUnitType() {
        return unitType;
    }

    @Override
    public Coords getUnitPosition() {
        return unitPosition;
    }

    @Override
    public void setUnitPosition(Coords newPosition) {
        this.unitPosition = newPosition;
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
}
