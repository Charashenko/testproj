package model;

import java.util.Objects;

/**
 * Class representing goods item.
 */
public class Goods {

    private final GoodsType goodsType;
    private double price;
    private double weight;

    /**
     * @param goodsType Type of goods from enum GoodsType.
     * @param price Price of goods.
     * @param weight Weight of goods.
     */
    public Goods(GoodsType goodsType, double price, double weight) {
        this.goodsType = goodsType;
        this.price = price;
        this.weight = weight;
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
     * Returns weight of goods.
     * @return Weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight of goods item.
     * @param weight Weight to be set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
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
        return Double.compare(goods.price, price) == 0 && Double.compare(goods.weight, weight) == 0 && goodsType == goods.goodsType;
    }

    /**
     * Returns hashcode of goods item from GoodsType, price and weight
     * @return Hashcode of goods item
     */
    @Override
    public int hashCode() {
        return Objects.hash(goodsType, price, weight);
    }
}
