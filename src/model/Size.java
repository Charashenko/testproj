package model;

/**
 * Class representing size of object specified with row count and column count
 */
public class Size {

    private int rowCount;
    private int columnCount;

    /**
     * Create size with specified row and column counts
     * @param rowCount Row count of size
     * @param columnCount Column count of size
     */
    public Size(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    /**
     * Gets row count of object's size
     * @return Row count
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Set's row count of object's size
     * @param rowCount Row count to be set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * Gets column count of object's size
     * @return Column count
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Set's column count of object's size
     * @param columnCount Column count to be set
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    /**
     * Get's size of area occupied by object
     * @return Area size
     */
    public int getAreaSize() {
        return getRowCount()*getColumnCount();
    }
}
