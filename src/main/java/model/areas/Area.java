package model.areas;

import model.Coords;
import model.Size;

public interface Area {

    AreaType getType();
    Coords getUpperLeft();
    Coords getLowerRight();
    Size getSize();
    void setUpperLeft(Coords c);
    void setLowerRight(Coords c);

}
