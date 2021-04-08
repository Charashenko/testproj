package model;

/**
 * Object representing position inside warehouse, using ROW and COLUMN.
 */
public class Coords {

    private int row;
    private int column;

    /**
     * Create coordinates from specified numbers.
     * @param row Integer corresponding to ROW position inside warehouse.
     * @param column Integer corresponding to COLUMN position inside warehouse.
     */
    public Coords(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets ROW coordinate of Coords.
     * @return Integer representing ROW position inside warehouse.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets ROW coordinate of Coords.
     * @param row Integer to be set as X coordinate.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets COLUMN coordinate of Coords.
     * @return Integer representing COLUMN position inside warehouse.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets COLUMN coordinate of Coords.
     * @param column Integer to be set as COLUMN coordinate.
     */
    public void setY(int column) {
        this.column = column;
    }
}
