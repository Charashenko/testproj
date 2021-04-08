package model.entities.physical;

import model.Coords;
import model.Goods;
import model.entities.Entity;
import model.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Shelf implements Entity {

    private final EntityType type = EntityType.SHELF;
    private Coords position;
    private List<Goods> goods = new ArrayList<>();

    public Shelf(Coords position) {
        this.position = position;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public Coords getPosition() {
        return position;
    }

    @Override
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
}
