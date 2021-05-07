package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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
    public WarehouseView(Text informationText) {
        this.sizeOfWarehouse = new Size(14, 22);
        this.informationText = informationText;
        unitViews = new ArrayList<>();
        guiWarehouse = new Pane();

        createDefaultUnitViews();
        drawGui();
    }

    public Pane getGuiWarehouse() {
        return guiWarehouse;
    }

    public void createDefaultUnitViews() {
        guiWarehouse = new Pane();

        //add paths everywhere
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                PathView pathView = new PathView(new Coords(row, col), informationText);
                unitViews.add(pathView);
                guiWarehouse.getChildren().add(pathView.getGuiPath());
            }
        }

        //add shelves
        List<ShelfView> shelfViews = parseShelfYaml();
        unitViews.addAll(shelfViews);
        guiWarehouse.getChildren().addAll(shelfViews.stream().map(ShelfView::getGuiShelf).collect(Collectors.toList()));

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
        cv.getCart().setTransportedGoods(Collections.singletonList(new Goods(GoodsType.GITARA, 25)));
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

        UnloadingView unloadingView = new UnloadingView(
                new Coords(sizeOfWarehouse.getRowCount()-1, sizeOfWarehouse.getColumnCount()-3), informationText);

        unitViews.add(unloadingView);
        guiWarehouse.getChildren().add(unloadingView.getGuiUnloading());
    }

    /**
     * Updates gui of warehouse
     */
    public void drawGui() {
        for (UnitView unitView : unitViews) {
            switch (unitView.getUnitType()) {
                case CARTVIEW:
                    Rectangle guiCart = ((CartView) unitView).getGuiCart();
                    guiCart.setTranslateX(30 * unitView.getUnitPosition().getColumn() + 3.75); // +3.75 because cart is slightly smaller
                    guiCart.setTranslateY(30 * unitView.getUnitPosition().getRow() + 3.75); // +3.75 because cart is slightly smaller
                    break;
                case SHELFVIEW:
                    Rectangle guiShelf = ((ShelfView) unitView).getGuiShelf();
                    guiShelf.setTranslateX(30 * unitView.getUnitPosition().getColumn());
                    guiShelf.setTranslateY(30 * unitView.getUnitPosition().getRow());
                    break;
                case PATHVIEW:
                    Rectangle guiPath = ((PathView) unitView).getGuiPath();
                    guiPath.setTranslateX(30 * unitView.getUnitPosition().getColumn());
                    guiPath.setTranslateY(30 * unitView.getUnitPosition().getRow());
                    break;
                case UNLOADINGVIEW:
                    Rectangle guiUnloading = ((UnloadingView) unitView).getGuiUnloading();
                    guiUnloading.setTranslateX(30 * unitView.getUnitPosition().getColumn());
                    guiUnloading.setTranslateY(30 * unitView.getUnitPosition().getRow());
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
        for (UnitView unitView : unitViews) {
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
                case UNLOADINGVIEW:
                    Rectangle guiUnloading = ((UnloadingView) unitView).getGuiUnloading();
                    guiWarehouse.getChildren().add(guiUnloading);
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
     *
     * @param coords Coordinates
     * @return UnitView
     */
    public UnitView getUnitViewAtCoords(Coords coords) {
        for (UnitView unitView : unitViews) {
            if (unitView.getUnitPosition().equals(coords)) {
                return unitView;
            }
        }
        return null;
    }

    /**
     * Gets PathView at specified coordinates
     * @param coords Coordinates
     * @return PathView
     */
    public PathView getPathViewAtCoords(Coords coords) {
        for (UnitView unitView : unitViews) {
            if (unitView.getUnitPosition().equals(coords) && unitView instanceof PathView) {
                return (PathView) unitView;
            }
        }
        return null;
    }

    /**
     * Initialization of warehouse from file
     */
    public List<ShelfView> parseShelfYaml() {
        List<ShelfView> shelfViews = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("data/initial_shelves_status.yaml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            for (String s : data.keySet()) {
                ShelfView shelfView;
                int row = 0, col = 0;
                LinkedHashMap<String, Object> unit = (LinkedHashMap<String, Object>) data.get(s);
                LinkedHashMap<String, Object> content = new LinkedHashMap<>();
                for (String s1 : unit.keySet()) {
                    switch (s1) {
                        case "col":
                            col = (int) unit.get(s1);
                            break;
                        case "row":
                            row = (int) unit.get(s1);
                            break;
                        case "content":
                            content = (LinkedHashMap<String, Object>) unit.get(s1);
                            break;
                    }
                }
                shelfView = new ShelfView(new Coords(row, col), informationText);
                for (String s2 : content.keySet()) {
                    for (int i = 0; i < (int) content.get(s2); i++) {
                        shelfView.getShelf().addGoods(new Goods(GoodsType.valueOf(s2), 10));
                    }
                }
                shelfViews.add(shelfView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelfViews;
    }

    public List<UnitView> parseUnitsYaml(){
        List<UnitView> unitViews = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("data/units.yaml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            for (String s : data.keySet()) {
                UnitView unitView = null;
                int row = 0, col = 0;
                LinkedHashMap<String, Object> unit = (LinkedHashMap<String, Object>) data.get(s);
                String type = null;
                for (String s1 : unit.keySet()) {
                    switch (s1) {
                        case "col":
                            col = (int) unit.get(s1);
                            break;
                        case "row":
                            row = (int) unit.get(s1);
                            break;
                        default:
                            type = String.valueOf(unit.get(s1));
                            break;
                    }
                    switch(type){
                        case "CART":
                            unitView = new CartView(new Coords(row,col),informationText);
                            break;
                        case "UNLOADING":
                            unitView = new UnloadingView(new Coords(row, col), informationText);
                    }
                }
                unitViews.add(unitView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitViews;
    }
}
