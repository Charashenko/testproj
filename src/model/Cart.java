package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart {

    private CartRoute plannedRoute;
    private List<Goods> transportedGoods;

    public Cart() {
        this.transportedGoods = new ArrayList<>();
    }

    public CartRoute getPlannedRoute(){
        return this.plannedRoute;
    }

    public void setPlannedRoute(CartRoute plannedRoute) {
        this.plannedRoute = plannedRoute;
    }

    public List<Goods> getTransportedGoods() {
        return transportedGoods;
    }

    public void setTransportedGoods(List<Goods> transportedGoods) {
        this.transportedGoods = transportedGoods;
    }

    public void addTransportedGoods(Goods goods){
        this.transportedGoods.add(goods);
    }
}
