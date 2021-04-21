package view;

import controller.OnCartInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Cart;
import model.Coords;

public class CartView {

    private Rectangle guiCart;
    private Cart cart;
    private Text informationText;
    private Color cartColor = Color.valueOf("1a4d4dff");

    public CartView(Coords position, Text informationText) {
        guiCart = new Rectangle(30,30, cartColor);
        guiCart.addEventHandler(MouseEvent.ANY, mouseEvent -> new OnCartInteraction().handle(mouseEvent, this));
        this.informationText = informationText;
        cart = new Cart(position);
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

    public void setCartColor(Color cartColor) {
        this.cartColor = cartColor;
    }
}
