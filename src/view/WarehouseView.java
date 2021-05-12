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
        this.sizeOfWarehouse = new Size(15, 25);
        this.informationText = informationText;
        unitViews = new ArrayList<>();
        guiWarehouse = new Pane();

        createDefaults();
        drawGui();
    }

    public Pane getGuiWarehouse() {
        return guiWarehouse;
    }

    public void createDefaults() {
        guiWarehouse = new Pane();

        //add shelves
        List<ShelfView> shelfViews = parseShelfYaml();
        unitViews.addAll(shelfViews);
        guiWarehouse.getChildren().addAll(shelfViews.stream().map(ShelfView::getGuiShelf).collect(Collectors.toList()));

        //add paths in empty space
        for (int row = 0; row < sizeOfWarehouse.getRowCount(); row++) {
            for (int col = 0; col < sizeOfWarehouse.getColumnCount(); col++) {
                if (getShelfViewAtCoords(new Coords(row, col)) == null) {
                    PathView pathView = new PathView(new Coords(row, col), informationText);
                    unitViews.add(pathView);
                    guiWarehouse.getChildren().add(pathView.getGuiPath());
                }
            }
        }

        //add carts and unloading areas
        List<UnitView> cartsAndUnloadingViews = parseUnitsYaml();
        unitViews.addAll(cartsAndUnloadingViews);
        guiWarehouse.getChildren().addAll(cartsAndUnloadingViews.stream().map(o -> {
            if (o.getUnitType().equals(UnitTypes.CARTVIEW)) {
                return ((CartView) o).getGuiCart();
            } else {
                return ((UnloadingView) o).getGuiUnloading();
            }
        }).collect(Collectors.toList()));
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

    public List<CartView> getCartViews() {
        List<CartView> carts = new ArrayList<>();
        for (UnitView unitView : unitViews) {
            if (unitView instanceof CartView) {
                carts.add((CartView) unitView);
            }
        }
        return carts;
    }

    public List<PathView> getPathViews() {
        List<PathView> paths = new ArrayList<>();
        for (UnitView unitView : unitViews) {
            if (unitView instanceof PathView) {
                paths.add((PathView) unitView);
            }
        }
        return paths;
    }

    public List<UnloadingView> getUnloadingViews(){
        List<UnloadingView> unloadingViews = new ArrayList<>();
        for (UnitView unitView : unitViews) {
            if (unitView instanceof UnloadingView) {
                unloadingViews.add((UnloadingView) unitView);
            }
        }
        return unloadingViews;
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
     *
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
     * Gets ShelfView at specified coordinates
     *
     * @param coords Coordinates
     * @return ShelfView
     */
    public ShelfView getShelfViewAtCoords(Coords coords) {
        for (UnitView unitView : unitViews) {
            if (unitView.getUnitPosition().equals(coords) && unitView instanceof ShelfView) {
                return (ShelfView) unitView;
            }
        }
        return null;
    }

    /**
     * Parse initial_shelves_status.yaml file to list of shelf views
     *
     * @return List of shelf views present in that file
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

    /**
     * Parse units.yaml file to list of unit views
     *
     * @return List of unit views present in that file
     */
    public List<UnitView> parseUnitsYaml() {
        List<UnitView> unitViews = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("data/units.yaml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            for (String s : data.keySet()) {
                UnitView unitView = null;
                int row = 0, col = 0;
                LinkedHashMap<String, Object> unit = (LinkedHashMap<String, Object>) data.get(s);
                String type;
                for (String s1 : unit.keySet()) {
                    switch (s1) {
                        case "col":
                            col = (int) unit.get(s1);
                            break;
                        case "row":
                            row = (int) unit.get(s1);
                            break;
                        default:
                            type = (String) unit.get(s1);
                            switch (type) {
                                case "CART":
                                    unitView = new CartView(new Coords(row, col), informationText, this);
                                    break;
                                case "UNLOADING":
                                    unitView = new UnloadingView(new Coords(row, col), informationText);
                            }
                    }
                }
                unitViews.add(unitView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitViews;
    }

    /**
     * Parse orders.yaml file
     *
     * @return List of orders present in that file
     */
    public List<Order> parseOrdersYaml() {
        List<Order> orders = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("data/orders.yaml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            for (String s : data.keySet()) {
                Order order = new Order();
                int id = 0;
                LinkedHashMap<String, Object> unit = (LinkedHashMap<String, Object>) data.get(s);
                LinkedHashMap<String, Object> content;
                for (String s1 : unit.keySet()) {
                    switch (s1) {
                        case "id":
                            id = (int) unit.get(s1);
                            order.setID(id);
                            break;
                        case "content":
                            content = (LinkedHashMap<String, Object>) unit.get(s1);
                            for (String s2 : content.keySet()) {
                                order.addGoodsToOrder(GoodsType.valueOf(s2), (Integer) content.get(s2));
                            }
                            break;
                    }
                }
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
