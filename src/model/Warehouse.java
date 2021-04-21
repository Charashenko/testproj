package model;

public class Warehouse {

    private final Area[][] warehouse;
    private final Size size;

    public Warehouse(Size sizeOfWarehouse) {
        this.warehouse = new Area[sizeOfWarehouse.getRowCount()][sizeOfWarehouse.getColumnCount()];
        this.size = sizeOfWarehouse;
    }

    public void addArea(Area a){
        Coords c1 = a.getUpperLeft();
        Coords c2 = a.getLowerRight();
        for (int row = c1.getRow(); row <= c2.getRow(); row++) {
            for (int col = c1.getColumn(); col <= c2.getColumn(); col++) {
                warehouse[row][col] = a;
            }
        }
    }

    public Area[][] getWarehouseLayout(){
        return this.warehouse;
    }

    public Size getSize(){
        return this.size;
    }

}
