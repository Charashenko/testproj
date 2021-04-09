package model.entities;

import model.Coords;

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
