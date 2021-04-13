package model;

/**
 * Object representing path for carts.
 */
public class Path {

    private Coords position;
    private Direction[] connectedPaths = new Direction[4];
    private Blockage blockage;

    public Coords getPosition() {
        return position;
    }

    /**
     * Set's position of path.
     * @param position Coordinates of path to be set.
     */
    public void setPosition(Coords position) {
        this.position = position;
    }

    /**
     * Get's connected paths
     * @return
     */
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
