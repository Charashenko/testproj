package view;

import controller.OnCartInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Cart;
import model.Coords;

public class CartView implements UnitView {

    private final Rectangle guiCart;
    private final Cart cart;
    private final Text informationText;
    private final Color cartColor = Color.valueOf("1a4d4dff");
    private final UnitTypes unitType = UnitTypes.SHELFVIEW;
    private Coords unitPosition;

    public CartView(Coords position, Text informationText) {
        this.guiCart = new Rectangle(30,30, cartColor);
        this.guiCart.addEventHandler(MouseEvent.ANY, mouseEvent -> new OnCartInteraction().handle(mouseEvent, this));
        this.informationText = informationText;
        this.cart = new Cart();
        this.unitPosition = position;
    }

    @Override
    public UnitTypes getUnitType() {
        return unitType;
    }

    @Override
    public Coords getUnitPosition() {
        return unitPosition;
    }

    @Override
    public void setUnitPosition(Coords newPosition) {
        this.unitPosition = newPosition;
    }

    public Rectangle getGuiCart() {
        return guiCart;
    }

    public Cart getCart() {
        return cart;
    }

    public Text getInformationText() {
        return informationText;
    }

    public Color getCartColor() {
        return cartColor;
    }
}
