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
    private final UnitTypes unitType = UnitTypes.CARTVIEW;
    private Coords unitPosition;

    public CartView(Coords position, Text informationText, WarehouseView warehouseView) {
        this.guiCart = new Rectangle(22.5,22.5, cartColor);
        this.guiCart.setArcHeight(5);
        this.guiCart.setArcWidth(5);
        this.guiCart.addEventHandler(MouseEvent.ANY, mouseEvent -> new OnCartInteraction().handle(mouseEvent, this));
        this.informationText = informationText;
        this.unitPosition = position;
        this.cart = new Cart(this, warehouseView);
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
