package view;

import controller.OnShelfInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;
import model.Goods;
import model.Shelf;

import java.util.List;

public class ShelfView {

    private final Rectangle guiShelf;
    private final Shelf shelf;
    private final Text informationText;

    public ShelfView(Coords c, Text informationText) {
        this.guiShelf = new Rectangle(60, 60, Color.DODGERBLUE);
        this.guiShelf.addEventHandler(MouseEvent.ANY, mouseEvent -> new OnShelfInteraction().handle(mouseEvent, this));
        this.shelf = new Shelf(c);
        this.informationText = informationText;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public Text getInformationText() {
        return informationText;
    }

    public Rectangle getGuiShelf() {
        return guiShelf;
    }

    public List<Goods> getShelfContents(){
        return shelf.getGoods();
    }
}
