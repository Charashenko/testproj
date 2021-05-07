package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;

public class UnloadingView implements UnitView {

    private final Rectangle guiUnloading;
    private final Text informationText;
    private final Color unloadingColor = Color.LIGHTSEAGREEN;
    private final UnitTypes unitType = UnitTypes.UNLOADINGVIEW;
    private final Coords unitPosition;

    public UnloadingView(Coords coords, Text informationText) {
        this.guiUnloading = new Rectangle(30, 30, unloadingColor);
        this.guiUnloading.setArcHeight(2.5);
        this.guiUnloading.setArcWidth(2.5);
        this.informationText = informationText;
        this.unitPosition = coords;
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
    }

    public Rectangle getGuiUnloading(){
        return guiUnloading;
    }
}
