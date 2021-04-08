package model;

public class Goods {

    private final GoodsType goodsType;
    private double price;
    private double weight;

    public Goods(GoodsType goodsType, double price, double weight) {
        this.goodsType = goodsType;
        this.price = price;
        this.weight = weight;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
