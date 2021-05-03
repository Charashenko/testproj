package view;

import model.Coords;

public interface UnitView {

    UnitTypes getUnitType();
    Coords getUnitPosition();
    void setUnitPosition(Coords newPosition);

}
