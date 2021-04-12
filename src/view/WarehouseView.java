package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
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
        guiWarehouse.setHgap(2);
        guiWarehouse.setVgap(2);
        guiWarehouse.setAlignment(Pos.CENTER);
        for (int i = 0; i < sizeOfWarehouse.getColumnCount(); i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            guiWarehouse.getColumnConstraints().add(cc);
        }
        warehouse = new Warehouse(sizeOfWarehouse);
        Coords start = new Coords(0, 0);
        Coords end = new Coords(sizeOfWarehouse.getRowCount()-1, 1);
        warehouse.addArea(new ShelvingArea(start, end));

        Coords start1 = new Coords(0, 4);
        Coords end1 = new Coords(sizeOfWarehouse.getRowCount()-1, 5);
        warehouse.addArea(new ShelvingArea(start1, end1));

    }

    public GridPane getGuiWarehouse() {
        return guiWarehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
