package model.areas;

import model.Area;
import model.Coords;
import model.Size;

/**
 *
 */
public class ShelvingArea implements Area {

    private Coords upperLeft;
    private Coords lowerRight;
    private Size size;
    private final AreaType type = AreaType.SHELVING;

    public ShelvingArea(Coords upperLeft, Coords lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

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
        calcSize();
        return size;
    }

    @Override
    public void setUpperLeft(Coords c) {
        this.upperLeft = c;
        calcSize();
    }

    @Override
    public void setLowerRight(Coords c) {
        this.lowerRight = c;
        calcSize();
    }

    private void calcSize(){
        this.size = new Size(lowerRight.getRow() - upperLeft.getRow(), lowerRight.getColumn() - upperLeft.getColumn());
    }
}
