package view;

import controller.OnCartInteraction;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Coords;
import model.entities.Cart;

public class CartView {

    private Rectangle guiCart;
    private Cart cart;
    private Text informationText;

    public CartView(Coords position, Text informationText) {
        guiCart = new Rectangle(30,30, Color.CRIMSON);
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
}
