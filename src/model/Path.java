package model;

/**
 * Object representing path for carts.
 */
public class Path {

    private boolean hasCart = false;
    private Direction[] connectedPaths = new Direction[4];
    private boolean blocked = false;

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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlockage(boolean toBeBlocked) {
        this.blocked = toBeBlocked;
    }

    public boolean hasCart() {
        return hasCart;
    }

    public void setHasCart(boolean hasCart) {
        this.hasCart = hasCart;
    }
}
