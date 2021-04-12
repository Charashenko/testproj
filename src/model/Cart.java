package model;

import java.util.Arrays;
import java.util.List;

public class Cart {

    private Coords position;
    private CartRoute plannedRoute;
    private List<Goods> transportedGoods;

    public Cart(Coords position) {
        this.position = position;
        setTransportedGoods(Arrays.asList(new Goods(GoodsType.GITARA, 24, 1), new Goods(GoodsType.GITARA, 24, 1),
                new Goods(GoodsType.STOL, 35, 15), new Goods(GoodsType.KOBEREC, 12, 0.5)));
    }

    public Coords getPosition() {
        return position;
    }

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
