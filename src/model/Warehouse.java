package model;

import javafx.scene.text.Text;
import model.areas.CartRouteArea;
import model.areas.ShelvingArea;
import view.CartView;
import view.PathView;
import view.ShelfView;
import view.UnitView;

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
        for (int i = 0; i < 5; i++) {
            start = new Coords(1, 1+3*i);
            end = new Coords(sizeOfWarehouse.getRowCount()-2, 2+3*i);
            addArea(new ShelvingArea(start, end));
        }
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
                        case SHELVING:
                            ShelfView sv = new ShelfView(new Coords(row, col), informationText);
                            sv.getShelf().addGoods(new Goods(GoodsType.GITARA, row, col));
                            warehouseUnitViews[row][col] = sv;
                            break;
                        case PATH:
                            PathView pv = new PathView(new Coords(row, col), informationText);
                            warehouseUnitViews[row][col] = pv;
                            break;
                        case PARKING:
                            CartView cv = new CartView(new Coords(row, col), informationText);
                            cv.getCart().addTransportedGoods(new Goods(GoodsType.STETEC, row, col));
                            warehouseUnitViews[row][col] = cv;
                            break;
                        case UNLOADING:
                            break;
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

    public Size getSize(){
        return this.size;
    }

}
