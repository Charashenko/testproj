package model.entities.physical;

import model.Coords;
import model.entities.Entity;
import model.entities.EntityType;

public class Blockage implements Entity {

    private final EntityType type = EntityType.BLOCKAGE;
    private Coords position;

    public Blockage(Coords position) {
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
