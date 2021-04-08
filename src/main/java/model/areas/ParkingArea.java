package model.areas;

import model.Coords;
import model.Size;

public class ParkingArea implements Area {

    private Coords upperLeft;
    private Coords lowerRight;
    private Size size;
    private final AreaType type = AreaType.PARKING;

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

    private void calcSize(){
        this.size = new Size(lowerRight.getRow() - upperLeft.getRow(), lowerRight.getColumn() - upperLeft.getColumn());
    }
}
