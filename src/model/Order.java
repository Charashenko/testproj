package model;

import java.util.HashMap;

public class Order {

    private HashMap<GoodsType, Integer> order = new HashMap<>();

    /**
     * Adds goods to order
     * @param goodsType Type of goods to be added
     * @param count Count of goods to be added
     */
    public void addGoodsToOrder(GoodsType goodsType, int count){
        if(order.containsKey(goodsType)){
            order.put(goodsType, order.get(goodsType)+count);
        } else {
            order.put(goodsType, count);
        }
    }

    /**
     * Adds goods to order
     * @param goodsType Type of goods to be removed
     * @param countToBeRemoved Count of goods to be removed
     */
    public void removeGoodsFromOrder(GoodsType goodsType, int countToBeRemoved){
        if(order.containsKey(goodsType)){
            int count = order.get(goodsType);
            if(count-countToBeRemoved<=0){
                order.remove(goodsType);
            } else {
                order.put(goodsType, count-countToBeRemoved);
            }
        }
    }

    /**
     * Returns formatted string for Text field to be displayed
     * @return Formatted string
     */
    public String getOrderItems(){
        String resultString = "";
        for(GoodsType goodsType : order.keySet()){
            int count = order.get(goodsType);
            resultString = String.format("%s%dx %s%n", resultString, count, goodsType.name());
        }
        return resultString;
    }

    /**
     * Clears all goods from current order
     */
    public void clearOrder(){
        order.clear();
    }
}
