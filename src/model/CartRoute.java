package model;

import java.util.HashMap;
import java.util.List;

public class CartRoute {

    private HashMap<Integer, Direction> directions;
    private int step = 0;

    public HashMap<Integer, Direction> getPlannedPath() {
        return directions;
    }

    public void setPlannedPath(HashMap<Integer, Direction> directions) {
        this.directions = directions;
    }

    public void removeRestOfThePath(int step){
        for (Integer i : directions.keySet()) {
            if(i >= step)
                directions.remove(i);
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
