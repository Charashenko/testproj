package view;

import controller.OnShelfInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;
import model.Goods;
import model.Shelf;

import java.util.List;

public class ShelfView implements UnitView {

    private final Rectangle guiShelf;
    private final Shelf shelf;
    private final Text informationText;
    private final Color shelfColor = Color.DODGERBLUE;
    private final UnitTypes unitType = UnitTypes.SHELFVIEW;
    private Coords unitPosition;

    public ShelfView(Coords position, Text informationText) {
        this.guiShelf = new Rectangle(60, 60, shelfColor);
        this.guiShelf.addEventHandler(MouseEvent.ANY, mouseEvent -> new OnShelfInteraction().handle(mouseEvent, this));
        this.shelf = new Shelf();
        this.informationText = informationText;
        this.unitPosition = position;
    }

    @Override
    public UnitTypes getUnitType() {
        return this.unitType;
    }

    @Override
    public Coords getUnitPosition() {
        return this.unitPosition;
    }

    @Override
    public void setUnitPosition(Coords newPosition) {
        this.unitPosition = newPosition;
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

    public Color getShelfColor(){
        return shelfColor;
    }
}
