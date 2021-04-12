package model;

import model.areas.AreaType;

public interface Area {

    AreaType getType();
    Coords getUpperLeft();
    Coords getLowerRight();
    Size getSize();
    void setUpperLeft(Coords c);
    void setLowerRight(Coords c);

}
