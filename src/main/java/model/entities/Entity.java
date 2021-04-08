package model.entities;

import model.Coords;

public interface Entity {

    EntityType getEntityType();
    Coords getPosition();
    void setPosition(Coords position);

}
