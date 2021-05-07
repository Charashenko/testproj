package model;

import java.util.Objects;

/**
 * Class representing goods item.
 */
public class Goods {

    private final GoodsType goodsType;
    private double price;

    /**
     * @param goodsType Type of goods from enum GoodsType.
     * @param price Price of goods.
     */
    public Goods(GoodsType goodsType, double price) {
        this.goodsType = goodsType;
        this.price = price;
    }

    /**
     * Returns type of goods.
     * @return Type of goods
     */
    public GoodsType getGoodsType() {
        return goodsType;
    }

    /**
     * Returns price of goods.
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price of goods item.
     * @param price Price to be set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Compare goods items based on GoodsType, weight and price.
     * @param o Goods item to be compared.
     * @return True, if equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return goodsType == goods.goodsType;
    }

    /**
     * Returns hashcode of goods item from GoodsType, price and weight
     * @return Hashcode of goods item
     */
    @Override
    public int hashCode() {
        return Objects.hash(goodsType, price);
    }
}
