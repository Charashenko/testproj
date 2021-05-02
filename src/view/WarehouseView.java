package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Coords;
import model.Size;
import model.Warehouse;
import model.areas.CartRouteArea;
import model.areas.ParkingArea;
import model.areas.ShelvingArea;

public class WarehouseView {

    private final GridPane guiWarehouse;
    private final Warehouse warehouse;

    public WarehouseView(Size sizeOfWarehouse) {
        // set grid style
        guiWarehouse = new GridPane();
        guiWarehouse.setGridLinesVisible(false);
        guiWarehouse.setHgap(2);
        guiWarehouse.setVgap(2);
        //guiWarehouse.setAlignment(Pos.CENTER);
        for (int i = 0; i < sizeOfWarehouse.getColumnCount(); i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            guiWarehouse.getColumnConstraints().add(cc);
        }

        warehouse = new Warehouse(sizeOfWarehouse); //default
        Coords start = new Coords(0, 1);
        Coords end = new Coords(sizeOfWarehouse.getRowCount()-1, sizeOfWarehouse.getColumnCount()-1);
        getWarehouse().addArea(new CartRouteArea(start, end));

        start = new Coords(0, 1);
        end = new Coords(sizeOfWarehouse.getRowCount()-1, 2);
        getWarehouse().addArea(new ShelvingArea(start, end));

        start = new Coords(0, 5);
        end = new Coords(sizeOfWarehouse.getRowCount()-4, 7);
        getWarehouse().addArea(new ShelvingArea(start, end));

        start = new Coords(0, 9);
        end = new Coords(0, 9);
        getWarehouse().addArea(new ParkingArea(start, end));

    }

    public GridPane getGuiWarehouse() {
        return guiWarehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
