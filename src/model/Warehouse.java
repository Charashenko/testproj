package model;

import model.areas.Area;
import model.areas.CartRouteArea;
import model.areas.WarehouseLayout;

public class Warehouse {

    public static void main(String[] args) {
        WarehouseLayout layout = new WarehouseLayout(10, 10);
        layout.addArea(new CartRouteArea(new Coords(0, 0), new Coords(4,1)));
        layout.addArea(new CartRouteArea(new Coords(0, 0), new Coords(1,4)));

        for (int row = 0; row < layout.getSize().getRowCount(); row++) {
            for (int col = 0; col < layout.getSize().getColumnCount(); col++) {
                Area a = layout.getWarehouseLayout()[row][col];
                if(a != null)
                    System.out.print(a.getType().toString().charAt(0));
            }
            System.out.println("");
        }
    }

}
