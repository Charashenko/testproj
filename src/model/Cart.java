package model;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import view.*;

import java.util.*;

public class Cart {

    private Coords homePosition;
    private CartView cartView;
    private CartRoute cartRoute;
    private List<Goods> transportedGoods;
    private Pathfinder pathfinder;

    public Cart(CartView cv, WarehouseView warehouseView) {
        this.cartView = cv;
        this.homePosition = cv.getUnitPosition();
        this.transportedGoods = new ArrayList<>();
        this.cartRoute = new CartRoute();
        this.pathfinder = new Pathfinder(warehouseView, cv);
    }

    public CartRoute getPlannedRoute() {
        return this.cartRoute;
    }

    public void setPlannedRoute(CartRoute plannedRoute) {
        this.cartRoute = plannedRoute;
    }

    public List<Goods> getTransportedGoods() {
        return transportedGoods;
    }

    public void setTransportedGoods(List<Goods> transportedGoods) {
        this.transportedGoods = transportedGoods;
    }

    public void addTransportedGoods(Goods goods) {
        this.transportedGoods.add(goods);
    }

    public Coords getHomePosition() {
        return homePosition;
    }

    public boolean hasAvaiableRoute() {
        if (cartRoute.getStep() >= cartRoute.getPlannedPath().values().size()) {
            return false;
        }
        return true;
    }

    public void nextStep(int clock, WarehouseView warehouseView) {
        Coords position = cartView.getUnitPosition();
        if (hasAvaiableRoute()) {
            TranslateTransition tt = new TranslateTransition();
            PathView nextPathView;
            switch (cartRoute.getPlannedPath().get(cartRoute.getStep())) {
                case UP:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow() - 1, position.getColumn()));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow() - 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByY(-30);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case DOWN:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow() + 1, position.getColumn()));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow() + 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByY(30);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case LEFT:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow(), position.getColumn() - 1));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() - 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByX(-30);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case RIGHT:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow(), position.getColumn() + 1));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() + 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByX(30);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case TAKEOUT:
                    if(warehouseView.getShelfViewAtCoords(position.oneLeft()) != null){
                        ShelfView shelfView = warehouseView.getShelfViewAtCoords(position.oneLeft());
                        for(Goods goods : new ArrayList<>(shelfView.getShelfContents())){
                            if(cartView.getCart().getPathfinder().getGoodsTypes().contains(goods.getGoodsType())){
                                this.addTransportedGoods(goods);
                                this.getPathfinder().removeGoodsType(goods.getGoodsType());
                                this.getPathfinder().removeShelfViewContainingGoods(shelfView);
                                shelfView.getShelf().takeOutGoods(goods.getGoodsType());
                            }
                        }
                    } else if(warehouseView.getShelfViewAtCoords(position.oneRight()) != null) {
                        ShelfView shelfView = warehouseView.getShelfViewAtCoords(position.oneRight());
                        for (Goods goods : new ArrayList<>(shelfView.getShelfContents())) {
                            if (cartView.getCart().getPathfinder().getGoodsTypes().contains(goods.getGoodsType())) {
                                this.addTransportedGoods(goods);
                                this.getPathfinder().removeGoodsType(goods.getGoodsType());
                                this.getPathfinder().removeShelfViewContainingGoods(shelfView);
                                shelfView.getShelf().takeOutGoods(goods.getGoodsType());
                            }
                        }
                    }
                    break;
            }
            tt.play();
        }
    }

    public void nextStepWithoutAnimation(WarehouseView warehouseView) {
        Coords position = cartView.getUnitPosition();
        if (hasAvaiableRoute()) {
            PathView nextPathView;
            switch (cartRoute.getPlannedPath().get(cartRoute.getStep())) {
                case UP:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow() - 1, position.getColumn()));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow() - 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case DOWN:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow() + 1, position.getColumn()));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow() + 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case LEFT:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow(), position.getColumn() - 1));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() - 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case RIGHT:
                    nextPathView = warehouseView.getPathViewAtCoords(new Coords(position.getRow(), position.getColumn() + 1));
                    if (!nextPathView.getPath().isBlocked()) {
                        nextPathView.getPath().setHasCart(true);
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() + 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        warehouseView.getPathViewAtCoords(position).getPath().setHasCart(false);
                    }
                    break;
                case TAKEOUT:
                    break;
            }
        }
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
