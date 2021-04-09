package model.entities;

import model.Coords;
import model.CartRoute;
import model.Goods;
import model.GoodsType;

import java.util.Collections;
import java.util.List;

public class Cart implements Entity {

    private Coords position;
    private CartRoute plannedRoute;
    private List<Goods> transportedGoods;

    public Cart(Coords position) {
        this.position = position;
        setTransportedGoods(Collections.singletonList(new Goods(GoodsType.GITARA, 24, 1)));
    }

    @Override
    public Coords getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coords position) {
        this.position = position;
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
}
