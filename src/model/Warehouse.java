package model;

import model.areas.Area;
import model.areas.CartRouteArea;
import model.areas.WarehouseLayout;

public class Warehouse {

    public Warehouse() {
        WarehouseLayout layout = new WarehouseLayout(10, 10);

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
