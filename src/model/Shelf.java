package model;

import java.util.ArrayList;
import java.util.List;

public class Shelf {

    private List<Goods> goods = new ArrayList<>();

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public void addGoods(Goods goods){
        this.goods.add(goods);
    }


    public Goods takeOutGoods(GoodsType wantedGoods) {
        for(Goods goodsItem : goods){
            if(goodsItem.getGoodsType().equals(wantedGoods)){
                goods.remove(goodsItem);
                return goodsItem;
            }
        }
        return null;
    }
}
