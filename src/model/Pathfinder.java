package model;

import view.*;

import java.util.ArrayList;
import java.util.List;

public class Pathfinder {

    private WarehouseView warehouseView;
    private Order currentOrder;

    public Pathfinder(WarehouseView warehouseView, Order currentOrder) {
        this.warehouseView = warehouseView;
        this.currentOrder = currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void calcPathForCart(CartView cartView){
        Coords homePosition = cartView.getCart().getHomePosition();
        List<ShelfView> shelfViewsContainingGoods = new ArrayList<>();
        for(UnitView unitView : warehouseView.getUnitViews()){
            if(unitView.getUnitType().equals(UnitTypes.SHELFVIEW)){
                ShelfView shelfView = (ShelfView) unitView;
                for(Goods goods : shelfView.getShelfContents()){
                    if(currentOrder.getOrderItems().containsKey(goods.getGoodsType())){
                        System.out.println(goods.getGoodsType());
                    }
                }
            }
        }
    }
}
