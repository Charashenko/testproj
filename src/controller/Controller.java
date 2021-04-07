package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Goods;
import view.ShelfView;

public class Controller {

    public static void exitApp(ActionEvent event) {
        System.exit(1);
    }

    public static void showAboutInfo(ActionEvent event) {
        //TODO About popup window
    }

    public static void importOrderFile(ActionEvent event) {
        System.out.println("Importing order file...");
        //TODO Importing of file with orders
    }

    public static void showShelfContents(MouseEvent event, ShelfView sv) {
        Rectangle r = (Rectangle) event.getTarget();
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            r.setFill(Color.GOLDENROD);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
            r.setFill(Color.DODGERBLUE);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            //TODO Update informationText to show contents
            System.out.println("[Shelf obsahuje]");
            for (Goods g :sv.getShelfContents()) {
                System.out.println(g.getGoodsType());
            }
        }

        event.consume();
    }



}
