package model;

import view.CartView;
import view.WarehouseView;

public class Pathfinder {

    private WarehouseView warehouseView;

    public Pathfinder(WarehouseView warehouseView) {
        this.warehouseView = warehouseView;
    }

    public void calcPathForCart(CartView cartView){
        Coords homePosition = cartView.getCart().getHomePosition();
    }
}
