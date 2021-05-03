package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.*;
import model.areas.CartRouteArea;
import model.areas.ParkingArea;
import model.areas.ShelvingArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarehouseView {

    private GridPane guiWarehouse;
    private UnitView[][] unitViews;
    private Size sizeOfWarehouse;
    private Text informationText;

    /**
     * Creates representation of warehouse.
     * Firstly creates warehouse and unit views of that warehouse layout, then creates gui representation of warehouse.
     * @param informationText Information panel text field
     */
    public WarehouseView(Size sizeOfWarehouse, Text informationText) {
        this.sizeOfWarehouse = sizeOfWarehouse;
        this.informationText = informationText;
        unitViews = new UnitView[sizeOfWarehouse.getRowCount()][sizeOfWarehouse.getColumnCount()];
        guiWarehouse = new GridPane();

        createDefaultUnitViews();
        updateGuiWarehouse();

        // set grid style
        guiWarehouse.setGridLinesVisible(false);
        guiWarehouse.setHgap(2);
        guiWarehouse.setVgap(2);
        guiWarehouse.setAlignment(Pos.CENTER);
        for (int i = 0; i < sizeOfWarehouse.getColumnCount(); i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            guiWarehouse.getColumnConstraints().add(cc);
        }
    }

    public GridPane getGuiWarehouse() {
        return guiWarehouse;
    }

    /**
     * Updates gui of warehouse
     */
    public void updateGuiWarehouse(){
        String infoTextString = informationText.getText();
        guiWarehouse.getChildren().clear();
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                UnitView unitView = unitViews[row][col];
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
        informationText.setText(infoTextString);
    }

    public void createDefaultUnitViews(){
        //add paths everywhere
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                unitViews[row][col] = new PathView(new Coords(row, col), informationText);
            }
        }
        //add shelves
        for (int i = 0; i < 7; i++) {
            for (int row = 1; row < 17; row++) {
                for (int col = 1+3*i; col < 3+3*i; col++) {
                    unitViews[row][col] = new ShelfView(new Coords(row, col), informationText);
                }
            }
        }
        //add carts
        CartView cv = new CartView(new Coords(sizeOfWarehouse.getRowCount()-1, 0), informationText);

        //default
        CartRoute cr = new CartRoute();
        HashMap<Integer, Direction> directions = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            directions.put(i, Direction.UP);
        }
        for (int i = 10; i < 20; i++) {
            directions.put(i, Direction.DOWN);
        }
        for (int i = 20; i < 30; i++) {
            directions.put(i, Direction.RIGHT);
        }
        for (int i = 30; i < 40; i++) {
            directions.put(i, Direction.LEFT);
        }
        cr.setPlannedPath(directions);
        cv.getCart().setPlannedRoute(cr);
        unitViews[sizeOfWarehouse.getRowCount()-1][0] = cv;

        cv = new CartView(new Coords(sizeOfWarehouse.getRowCount()-1, sizeOfWarehouse.getColumnCount()-1), informationText);
        cr = new CartRoute();
        directions = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            directions.put(i, Direction.UP);
        }
        for (int i = 10; i < 20; i++) {
            directions.put(i, Direction.DOWN);
        }
        for (int i = 20; i < 40; i++) {
            directions.put(i, Direction.LEFT);
        }
        for (int i = 40; i < 60; i++) {
            directions.put(i, Direction.RIGHT);
        }
        cr.setPlannedPath(directions);
        cv.getCart().setPlannedRoute(cr);
        unitViews[sizeOfWarehouse.getRowCount()-1][sizeOfWarehouse.getColumnCount()-1] = cv;

        cv = new CartView(new Coords(sizeOfWarehouse.getRowCount()-1, 6), informationText);
        unitViews[sizeOfWarehouse.getRowCount()-1][6] = cv;
    }

    public UnitView[][] getUnitViews(){
        return this.unitViews;
    }

    public List<CartView> getCartViews(){
        List<CartView> carts = new ArrayList<>();
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                if(unitViews[row][col].getUnitType().equals(UnitTypes.CARTVIEW)){
                    carts.add((CartView) unitViews[row][col]);
                }
            }
        }
        return carts;
    }
}
