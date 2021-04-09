package model.entities;

import model.Coords;

public interface Entity {

    Coords getPosition();
    void setPosition(Coords position);

}
