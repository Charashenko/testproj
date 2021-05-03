package model;

import view.CartView;
import view.PathView;
import view.UnitTypes;
import view.UnitView;

import java.util.*;

public class Cart {

    private PathView occupiedPathView;
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

    public void nextStep(UnitView[][] unitViews){
        try {
            Coords position = cartView.getUnitPosition();
            if(hasAvaiableRoute()) {
                switch (cartRoute.getPlannedPath().get(cartRoute.getStep())) {
                    case UP:
                        if (occupiedPathView == null) {
                            occupiedPathView = new PathView(position, cartView.getInformationText());
                        }
                        unitViews[position.getRow()][position.getColumn()] = occupiedPathView;
                        occupiedPathView = (PathView) unitViews[position.getRow() - 1][position.getColumn()];
                        unitViews[position.getRow() - 1][position.getColumn()] = cartView;
                        cartView.setUnitPosition(new Coords(position.getRow() - 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        break;
                    case DOWN:
                        if (occupiedPathView == null) {
                            occupiedPathView = new PathView(position, cartView.getInformationText());
                        }
                        unitViews[position.getRow()][position.getColumn()] = occupiedPathView;
                        occupiedPathView = (PathView) unitViews[position.getRow() + 1][position.getColumn()];
                        unitViews[position.getRow() + 1][position.getColumn()] = cartView;
                        cartView.setUnitPosition(new Coords(position.getRow() + 1, position.getColumn()));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        break;
                    case LEFT:
                        if (occupiedPathView == null) {
                            occupiedPathView = new PathView(position, cartView.getInformationText());
                        }
                        unitViews[position.getRow()][position.getColumn()] = occupiedPathView;
                        UnitView unitView = unitViews[position.getRow()][position.getColumn() - 1];
                        if(unitView.getUnitType().equals(UnitTypes.PATHVIEW)) {
                            occupiedPathView = (PathView) unitView;
                        }
                        unitViews[position.getRow()][position.getColumn() - 1] = cartView;
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() - 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
                        break;
                    case RIGHT:
                        if (occupiedPathView == null) {
                            occupiedPathView = new PathView(position, cartView.getInformationText());
                        }
                        unitViews[position.getRow()][position.getColumn()] = occupiedPathView;
                        occupiedPathView = (PathView) unitViews[position.getRow()][position.getColumn() + 1];
                        unitViews[position.getRow()][position.getColumn() + 1] = cartView;
                        cartView.setUnitPosition(new Coords(position.getRow(), position.getColumn() + 1));
                        cartRoute.setStep(cartRoute.getStep() + 1);
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
