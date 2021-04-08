package model.entities.physical;

import model.Coords;
import model.entities.Entity;
import model.entities.EntityType;
import model.entities.path_related.Direction;

public class Path implements Entity {

    private final EntityType type = EntityType.SHELF;
    private Coords position;
    private Direction[] connectedPaths = new Direction[4];
    private Blockage blockage;

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

    public Direction[] getConnectedPaths() {
        return connectedPaths;
    }

    public void setConnectedPaths(Direction[] connectedPaths) {
        this.connectedPaths = connectedPaths;
    }

    public Blockage getBlockage() {
        return blockage;
    }

    public boolean isBlocked() {
        return getBlockage() != null;
    }

    public void setBlockage(Blockage blockage) {
        this.blockage = blockage;
    }
}
