package model;

import model.areas.Area;

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
        for (int row = 0; row < (c2.getRow() - c1.getRow())+1; row++) {
            for (int col = 0; col < (c2.getColumn() - c1.getColumn())+1; col++) {
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
