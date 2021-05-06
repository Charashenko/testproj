package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WarehouseView {

    private Pane guiWarehouse;
    private List<UnitView> unitViews;
    private Size sizeOfWarehouse;
    private Text informationText;

    /**
     * Creates representation of warehouse.
     * Firstly creates warehouse and unit views of that warehouse layout, then creates gui representation of warehouse.
     *
     * @param informationText Information panel text field
     */
    public WarehouseView(Size sizeOfWarehouse, Text informationText) {
        this.sizeOfWarehouse = sizeOfWarehouse;
        this.informationText = informationText;
        unitViews = new ArrayList<>();
        guiWarehouse = new Pane();

        createDefaultUnitViews();
        drawGui();
    }

    public Pane getGuiWarehouse() {
        return guiWarehouse;
    }

    public void createDefaultUnitViews() { //default
        guiWarehouse.getChildren().clear();
        //add paths everywhere
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                PathView pathView = new PathView(new Coords(row, col), informationText);
                unitViews.add(pathView);
                guiWarehouse.getChildren().add(pathView.getGuiPath());
            }
        }
        //add shelves
        for (int i = 0; i < 7; i++) {
            for (int row = 1; row < 17; row++) {
                for (int col = 1 + 3 * i; col < 3 + 3 * i; col++) {
                    ShelfView shelfView = new ShelfView(new Coords(row, col), informationText);
                    unitViews.add(shelfView);
                    guiWarehouse.getChildren().add(shelfView.getGuiShelf());
                }
            }
        }
        //add carts
        CartView cv = new CartView(new Coords(sizeOfWarehouse.getRowCount() - 1, 0), informationText);

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
        unitViews.add(cv);
        guiWarehouse.getChildren().add(cv.getGuiCart());

        cv = new CartView(new Coords(sizeOfWarehouse.getRowCount() - 1, sizeOfWarehouse.getColumnCount() - 1), informationText);
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
        unitViews.add(cv);
        guiWarehouse.getChildren().add(cv.getGuiCart());

        cv = new CartView(new Coords(0, 0), informationText);
        cv.getCart().setTransportedGoods(Collections.singletonList(new Goods(GoodsType.GITARA, 25, 1.8)));
        cr = new CartRoute();
        directions = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            directions.put(i, Direction.DOWN);
        }
        for (int i = 10; i < 20; i++) {
            directions.put(i, Direction.UP);
        }
        for (int i = 20; i < 40; i++) {
            directions.put(i, Direction.RIGHT);
        }
        for (int i = 40; i < 60; i++) {
            directions.put(i, Direction.LEFT);
        }
        cr.setPlannedPath(directions);
        cv.getCart().setPlannedRoute(cr);
        unitViews.add(cv);
        guiWarehouse.getChildren().add(cv.getGuiCart());

        cv = new CartView(new Coords(sizeOfWarehouse.getRowCount() - 1, 6), informationText);
        unitViews.add(cv);
        guiWarehouse.getChildren().add(cv.getGuiCart());
    }

    /**
     * Updates gui of warehouse
     */
    public void drawGui() {
        for (UnitView unitView : unitViews) {
            switch (unitView.getUnitType()) {
                case CARTVIEW:
                    Rectangle guiCart = ((CartView) unitView).getGuiCart();
                    guiCart.setTranslateX(60 * unitView.getUnitPosition().getColumn() + 7.5); //+7,5 because cart is slightly smaller
                    guiCart.setTranslateY(60 * unitView.getUnitPosition().getRow() + 7.5); //+7,5 because cart is slightly smaller
                    break;
                case SHELFVIEW:
                    Rectangle guiShelf = ((ShelfView) unitView).getGuiShelf();
                    guiShelf.setTranslateX(60 * unitView.getUnitPosition().getColumn());
                    guiShelf.setTranslateY(60 * unitView.getUnitPosition().getRow());
                    break;
                case PATHVIEW:
                    Rectangle guiPath = ((PathView) unitView).getGuiPath();
                    guiPath.setTranslateX(60 * unitView.getUnitPosition().getColumn());
                    guiPath.setTranslateY(60 * unitView.getUnitPosition().getRow());
                    break;
            }
        }
    }

    public List<UnitView> getUnitViews() {
        return unitViews;
    }

    public void setUnitViews(List<UnitView> unitViews) {
        this.unitViews = unitViews;
        guiWarehouse.getChildren().clear();
        for(UnitView unitView : unitViews){
            switch (unitView.getUnitType()) {
                case CARTVIEW:
                    Rectangle guiCart = ((CartView) unitView).getGuiCart();
                    guiWarehouse.getChildren().add(guiCart);
                    break;
                case SHELFVIEW:
                    Rectangle guiShelf = ((ShelfView) unitView).getGuiShelf();
                    guiWarehouse.getChildren().add(guiShelf);
                    break;
                case PATHVIEW:
                    Rectangle guiPath = ((PathView) unitView).getGuiPath();
                    guiWarehouse.getChildren().add(guiPath);
                    break;
            }
        }
    }

    public List<CartView> getCartViews() {
        List<CartView> carts = new ArrayList<>();
        for (UnitView unitView : unitViews) {
            if (unitView instanceof CartView) {
                carts.add((CartView) unitView);
            }
        }
        return carts;
    }

    /**
     * Gets UnitView at specified coordinates
     * @param coords Coords
     * @return UnitView
     */
    public UnitView getUnitViewAtCoords(Coords coords){
        for(UnitView unitView : unitViews){
            if(unitView.getUnitPosition().equals(coords)){
                return unitView;
            }
        }
        return null;
    }

    public PathView getPathViewAtCoords(Coords coords){
        for(UnitView unitView : unitViews){
            if(unitView.getUnitPosition().equals(coords) && unitView instanceof PathView){
                return (PathView) unitView;
            }
        }
        return null;
    }
}
