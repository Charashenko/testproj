package model;

import view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Order {

    private HashMap<GoodsType, Integer> order = new HashMap<>();
    private int ID;

    /**
     * Adds goods to order
     * @param goodsType Type of goods to be added
     * @param count Count of goods to be added
     */
    public void addGoodsToOrder(GoodsType goodsType, int count){
        if(order.containsKey(goodsType)){
            order.put(goodsType, order.get(goodsType)+count);
        } else {
            order.put(goodsType, count);
        }
    }

    /**
     * Adds goods to order
     * @param goodsType Type of goods to be removed
     * @param countToBeRemoved Count of goods to be removed
     */
    public void removeGoodsFromOrder(GoodsType goodsType, int countToBeRemoved){
        if(order.containsKey(goodsType)){
            int count = order.get(goodsType);
            if(count-countToBeRemoved<=0){
                order.remove(goodsType);
            } else {
                order.put(goodsType, count-countToBeRemoved);
            }
        }
    }

    /**
     * Returns formatted string for Text field to be displayed
     * @return Formatted string
     */
    public String getOrderItemsAsString(){
        String resultString = "";
        for(GoodsType goodsType : order.keySet()){
            int count = order.get(goodsType);
            resultString = String.format("%s%dx %s%n", resultString, count, goodsType.name());
        }
        return resultString;
    }

    /**
     * Clears all goods from current order
     */
    public void clearOrder(){
        order.clear();
    }

    /**
     * Gets all order items and their count
     * @return Hashmap of goods type and count
     */
    public HashMap<GoodsType, Integer> getOrderItems() {
        return order;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Divides current order between carts
     */
    public void divideCurrentOrder(WarehouseView warehouseView){
        int goodsCount;
        List<ShelfView> shelfViewsContainingGoods;
        List<GoodsType> goodsTypes;
        for(CartView cartView : warehouseView.getCartViews()) {
            goodsCount = 0;
            shelfViewsContainingGoods = new ArrayList<>();
            goodsTypes = new ArrayList<>();
            for (UnitView unitView : warehouseView.getUnitViews()) {
                if (unitView.getUnitType().equals(UnitTypes.SHELFVIEW)) {
                    ShelfView shelfView = (ShelfView) unitView;
                    for (Goods goods : shelfView.getShelfContents()) {
                        if (order.containsKey(goods.getGoodsType())) {
                            shelfViewsContainingGoods.add(shelfView);
                            goodsTypes.add(goods.getGoodsType());
                            removeGoodsFromOrder(goods.getGoodsType(), 1);
                            goodsCount++;
                            if (goodsCount == 5) {
                                break;
                            }
                        }
                    }
                    if (goodsCount == 5) {
                        break;
                    }
                }
            }
            System.out.println(goodsTypes);

            System.out.println(shelfViewsContainingGoods);
            Coords homePosition = cartView.getCart().getHomePosition();

            List<Coords> stops = new ArrayList<>();
            for (ShelfView shelfView : shelfViewsContainingGoods) {
                if (warehouseView.getUnitViewAtCoords(
                        new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() - 1))
                        .getUnitType().equals(UnitTypes.PATHVIEW)) {
                    System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() - 1));
                    stops.add(shelfView.getUnitPosition().oneLeft());
                } else if (warehouseView.getUnitViewAtCoords(
                        new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() + 1))
                        .getUnitType().equals(UnitTypes.PATHVIEW)) {
                    System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() + 1));
                    stops.add(shelfView.getUnitPosition().oneRight());
                }
            }
            if(!stops.isEmpty()) {
                UnloadingView unloadingView = warehouseView.getUnloadingViews().get(new Random().nextInt(warehouseView.getUnloadingViews().size() - 1));
                stops.add(unloadingView.getUnitPosition());
                System.out.println(unloadingView.getUnitPosition() + " " + homePosition);
                stops.add(cartView.getCart().getHomePosition());
                System.out.println(stops);
                cartView.getCart().getPathfinder().setStops(stops);
                cartView.getCart().getPathfinder().setShelfViewsContainingGoods(shelfViewsContainingGoods);
                cartView.getCart().getPathfinder().setGoodsTypes(goodsTypes);
            }
        }
    }
}
