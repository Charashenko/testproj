package view;

import controller.OnPathInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import model.Coords;
import model.Path;

public class PathView implements UnitView {

    private final Rectangle guiPath;
    private final Path path;
    private final Color pathColor = Color.WHITESMOKE;
    private final Color blockedPathColor = Color.INDIANRED;
    private final Color cartRouteColor = Color.DARKMAGENTA;
    private final Text informationText;
    private final UnitTypes unitType = UnitTypes.PATHVIEW;
    private Coords unitPosition;
    private boolean showingCartRoute = false;

    public PathView(Coords position, Text informationText) {
        this.guiPath = new Rectangle(30, 30, pathColor);
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

    public Rectangle getGuiPath() {
        return guiPath;
    }

    public Path getPath() {
        return path;
    }

    public void updatePathColor(){
        if(getPath().isBlocked()){
            getGuiPath().setFill(blockedPathColor);
            this.guiPath.setArcHeight(2.5);
            this.guiPath.setArcWidth(2.5);
            this.guiPath.setScaleX(1);
            this.guiPath.setScaleY(1);
        } else if (showingCartRoute){
            getGuiPath().setFill(cartRouteColor);
            this.guiPath.setArcHeight(30);
            this.guiPath.setArcWidth(30);
            this.guiPath.setScaleX(0.5);
            this.guiPath.setScaleY(0.5);
        } else {
            getGuiPath().setFill(pathColor);
            this.guiPath.setArcHeight(2.5);
            this.guiPath.setArcWidth(2.5);
            this.guiPath.setScaleX(1);
            this.guiPath.setScaleY(1);
        }
    }

    public Text getInformationText() {
        return informationText;
    }

    public void setShowingCartRoute(boolean showingCartRoute) {
        this.showingCartRoute = showingCartRoute;
    }
}
