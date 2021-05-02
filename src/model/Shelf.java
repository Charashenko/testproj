package model;

import java.util.ArrayList;
import java.util.List;

public class Shelf {

    private Coords position;
    private List<Goods> goods = new ArrayList<>();

    public Shelf(Coords position) {
        this.position = position;
    }

    public Coords getPosition() {
        return position;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public void addGoods(Goods goods){
        this.goods.add(goods);
    }

    public List<Goods> takeOutGoods(List<Goods> wantedGoods) {
        return null;
    }
}
