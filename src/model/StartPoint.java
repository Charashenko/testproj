package model;

import view.UnitView;

import java.util.List;

/**
 * Used as start data storage
 */
public class StartPoint {

    private List<UnitView> unitViews;

    public StartPoint(List<UnitView> unitViews) {
        this.unitViews = unitViews;
    }

    public List<UnitView> getUnitViews() {
        return unitViews;
    }
}
