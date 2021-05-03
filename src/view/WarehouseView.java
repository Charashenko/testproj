package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Coords;
import model.Size;
import model.Warehouse;
import model.areas.CartRouteArea;
import model.areas.ParkingArea;
import model.areas.ShelvingArea;

public class WarehouseView {

    private GridPane guiWarehouse;
    private Warehouse warehouse;

    public WarehouseView(Size sizeOfWarehouse, Text informationText) {
        createWarehouse(sizeOfWarehouse, informationText);
        // set grid style
        guiWarehouse.setGridLinesVisible(false);
        guiWarehouse.setHgap(2);
        guiWarehouse.setVgap(2);
        //guiWarehouse.setAlignment(Pos.CENTER);
        for (int i = 0; i < sizeOfWarehouse.getColumnCount(); i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            guiWarehouse.getColumnConstraints().add(cc);
        }
    }

    public GridPane getGuiWarehouse() {
        return guiWarehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Creates representation of warehouse.
     * Firstly creates warehouse and unit views of that warehouse layout, then creates gui representation of warehouse.
     * @param informationText Information panel text field
     * @param sizeOfWarehouse Size of warehouse
     */
    public void createWarehouse(Size sizeOfWarehouse, Text informationText){
        warehouse = new Warehouse(sizeOfWarehouse);
        warehouse.createWarehouseUnitViews(informationText);

        guiWarehouse = new GridPane();
        for (int row = 0; row < warehouse.getSize().getRowCount(); row++) {
            for (int col = 0; col < warehouse.getSize().getColumnCount(); col++) {
                UnitView unitView = getWarehouse().getWarehouseUnitViews()[row][col];
                switch(unitView.getUnitType()){
                    case CARTVIEW:
                        guiWarehouse.add(((CartView) unitView).getGuiCart(), col, row);
                        break;
                    case SHELFVIEW:
                        guiWarehouse.add(((ShelfView) unitView).getGuiShelf(), col, row);
                        break;
                    case PATHVIEW:
                        guiWarehouse.add(((PathView) unitView).getGuiPath(), col, row);
                        break;
                }
            }
        }
    }
}
