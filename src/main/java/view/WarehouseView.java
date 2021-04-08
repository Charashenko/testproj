package view;

import javafx.scene.layout.GridPane;
import model.Coords;
import model.Size;
import model.Warehouse;
import model.areas.ShelvingArea;

public class WarehouseView {

    private GridPane guiWarehouse;
    private Warehouse warehouse;

    public WarehouseView(Size sizeOfWarehouse) {
        guiWarehouse = new GridPane();
        guiWarehouse.setGridLinesVisible(false);
        warehouse = new Warehouse(sizeOfWarehouse);
        Coords start = new Coords(0, 0);
        Coords end = new Coords(sizeOfWarehouse.getRowCount()-1, 1);
        warehouse.addArea(new ShelvingArea(start, end));
    }

    public GridPane getGuiWarehouse() {
        return guiWarehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
