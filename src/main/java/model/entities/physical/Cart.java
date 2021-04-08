package model.entities.physical;

import model.Coords;
import model.entities.Entity;
import model.entities.EntityType;
import model.entities.path_related.CartRoute;

public class Cart implements Entity {

    private final EntityType type = EntityType.CART;
    private Coords position;
    private CartRoute plannedRoute;

    public Cart(Coords position) {
        this.position = position;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public Coords getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coords position) {
        this.position = position;
    }

    public CartRoute getPlannedRoute(){
        return this.plannedRoute;
    }

    public void setPlannedRoute(CartRoute plannedRoute) {
        this.plannedRoute = plannedRoute;
    }
}
