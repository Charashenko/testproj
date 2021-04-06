package model;

/**
 * Object representing position inside warehouse, using X parameter as ROW and Y parameter as COLUMN.
 */
public class Coords {

    private int x;
    private int y;

    /**
     * Create coordinates from specified numbers.
     * @param x Integer corresponding to X/ROW position inside warehouse.
     * @param y Integer corresponding to Y/COLUMN position inside warehouse.
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X coordinate of Coords.
     * @return Integer representing X/ROW position inside warehouse.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets X coordinate of Coords.
     * @param x Integer to be set as X coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets Y coordinate of Coords.
     * @return Integer representing Y/COLUMN position inside warehouse.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets Y coordinate of Coords.
     * @param y Integer to be set as Y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }
}
