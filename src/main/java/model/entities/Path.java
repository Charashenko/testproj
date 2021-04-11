package model.entities;

import model.Coords;
import model.Direction;

public class Path {

    private Coords position;
    private Direction[] connectedPaths = new Direction[4];
    private Blockage blockage;

    public Coords getPosition() {
        return position;
    }

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
