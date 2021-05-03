package model;

import javafx.scene.text.Text;
import model.areas.CartRouteArea;
import model.areas.ParkingArea;
import model.areas.ShelvingArea;
import view.*;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final Area[][] warehouseLayout;
    private final UnitView[][] warehouseUnitViews;
    private final Size size;

    public Warehouse(Size sizeOfWarehouse) {
        this.warehouseLayout = new Area[sizeOfWarehouse.getRowCount()][sizeOfWarehouse.getColumnCount()];
        this.warehouseUnitViews = new UnitView[sizeOfWarehouse.getRowCount()][sizeOfWarehouse.getColumnCount()];
        this.size = sizeOfWarehouse;

        //default warehouse layout
        Coords start = new Coords(0, 0);
        Coords end = new Coords(sizeOfWarehouse.getRowCount()-1, sizeOfWarehouse.getColumnCount()-1);
        addArea(new CartRouteArea(start, end));
        for (int i = 0; i < 7; i++) {
            start = new Coords(1, 1+3*i);
            end = new Coords(17, 2+3*i);
            addArea(new ShelvingArea(start, end));
        }
        start = new Coords(sizeOfWarehouse.getRowCount()-1, sizeOfWarehouse.getColumnCount()-1);
        end = start;
        addArea(new ParkingArea(start, end));
        //end of default layout
    }

    public void addArea(Area a){
        Coords c1 = a.getUpperLeft();
        Coords c2 = a.getLowerRight();
        for (int row = c1.getRow(); row <= c2.getRow(); row++) {
            for (int col = c1.getColumn(); col <= c2.getColumn(); col++) {
                warehouseLayout[row][col] = a;
            }
        }
    }

    public void createWarehouseUnitViews(Text informationText){
        for (int row = 0; row < size.getRowCount(); row++) {
            for (int col = 0; col < size.getColumnCount(); col++) {
                if(getWarehouseLayout()[row][col] != null) {
                    switch (getWarehouseLayout()[row][col].getType()) {

                    }
                }
            }
        }
    }

    public Area[][] getWarehouseLayout(){
        return this.warehouseLayout;
    }

    public UnitView[][] getWarehouseUnitViews() {
        return this.warehouseUnitViews;
    }

    public List<CartView> getCartViews(){
        List<CartView> carts = new ArrayList<>();
        for (int row = 0; row < size.getRowCount(); row++) {
            for (int col = 0; col < size.getColumnCount(); col++) {
                if(warehouseUnitViews[row][col].getUnitType().equals(UnitTypes.CARTVIEW)){
                    carts.add((CartView) warehouseUnitViews[row][col]);
                }
            }
        }
        return carts;
    }

    public List<ShelfView> getShelfViews(){
        List<ShelfView> shelves = new ArrayList<>();
        for (int row = 0; row < size.getRowCount(); row++) {
            for (int col = 0; col < size.getColumnCount(); col++) {
                if(warehouseUnitViews[row][col].getUnitType().equals(UnitTypes.SHELFVIEW)){
                    shelves.add((ShelfView) warehouseUnitViews[row][col]);
                }
            }
        }
        return shelves;
    }

    public Size getSize(){
        return this.size;
    }

}
