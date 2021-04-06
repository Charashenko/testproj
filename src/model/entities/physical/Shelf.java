package model.entities.physical;

import model.Coords;
import model.entities.Entity;
import model.entities.EntityType;

public class Shelf implements Entity {

    private final EntityType type = EntityType.SHELF;
    private Coords position;

    public Shelf(Coords position) {
        this.position = position;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public Coords getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coords position) {
        this.position = position;
    }
}
