package model.areas;

import model.Area;
import model.Coords;
import model.Size;

public class UnloadingArea implements Area {

    private Coords upperLeft;
    private Coords lowerRight;
    private Size size;
    private final AreaType type = AreaType.UNLOADING;

    @Override
    public AreaType getType() {
        return type;
    }

    @Override
    public Coords getUpperLeft() {
        return upperLeft;
    }

    @Override
    public Coords getLowerRight() {
        return lowerRight;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void setUpperLeft(Coords c) {
        this.upperLeft = c;
    }

    @Override
    public void setLowerRight(Coords c) {
        this.lowerRight = c;
    }
}
