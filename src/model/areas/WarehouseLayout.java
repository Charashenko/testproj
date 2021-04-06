package model.areas;

import model.Coords;
import model.Size;

public class WarehouseLayout {

    private final Area[][] warehouseLayout;
    private final Size size;

    public WarehouseLayout(int rows, int cols) {
        this.warehouseLayout = new Area[rows][cols];
        this.size = new Size(rows, cols);
    }

    public void addArea(Area a){
        Coords c1 = a.getUpperLeft();
        Coords c2 = a.getLowerRight();
        for (int row = 0; row < (c2.getX() - c1.getX()); row++) {
            for (int col = 0; col < (c2.getY() - c1.getY()); col++) {
                warehouseLayout[row][col] = a;
            }
        }
    }

    public Area[][] getWarehouseLayout(){
        return this.warehouseLayout;
    }

    public Size getSize(){
        return this.size;
    }

}
