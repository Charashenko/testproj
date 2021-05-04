package model;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import view.CartView;
import view.PathView;
import view.UnitTypes;
import view.UnitView;

import java.util.*;

public class Cart {

    private Coords homePosition;
    private CartView cartView;
    private CartRoute cartRoute;
    private List<Goods> transportedGoods;

    public Cart(CartView cv) {
        this.cartView = cv;
        this.homePosition = cv.getUnitPosition();
        this.transportedGoods = new ArrayList<>();
        this.cartRoute = new CartRoute();
    }

    public CartRoute getPlannedRoute(){
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

    public void addTransportedGoods(Goods goods){
        this.transportedGoods.add(goods);
    }

    public Coords getHomePosition() {
        return homePosition;
    }

    public boolean hasAvaiableRoute(){
        if(getPlannedRoute() == null){
            return false;
        }
        if(cartRoute.getStep() >= cartRoute.getPlannedPath().values().size()){
            return false;
        }
        return true;
    }

    public void nextStep(int clock){
        try {
            Coords position = cartView.getUnitPosition();
            TranslateTransition tt;
            if(hasAvaiableRoute()) {
                switch (cartRoute.getPlannedPath().get(cartRoute.getStep())) {
                    case UP:
                        cartView.setUnitPosition(new Coords(position.getRow() - 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByY(-60);
                        tt.setCycleCount(0);
                        tt.setAutoReverse(false);
                        tt.play();
                        break;
                    case DOWN:
                        cartView.setUnitPosition(new Coords(position.getRow() + 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByY(60);
                        tt.setCycleCount(0);
                        tt.setAutoReverse(false);
                        tt.play();
                        break;
                    case LEFT:
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() - 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByX(-60);
                        tt.setCycleCount(0);
                        tt.setAutoReverse(false);
                        tt.play();
                        break;
                    case RIGHT:
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() + 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        tt = new TranslateTransition(Duration.millis(clock), cartView.getGuiCart());
                        tt.setByX(60);
                        tt.setCycleCount(0);
                        tt.setAutoReverse(false);
                        tt.play();
                        break;
                    default:
                        break;
                }
            }
        } catch (NullPointerException e){
            System.out.println("help");
        }
    }
}
