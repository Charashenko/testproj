package model.entities.path_related;

import java.util.HashMap;
import java.util.List;

public class CartRoute {

    private HashMap<Integer, Direction> directions;
    private int step = 0;

    public List<Direction> getPlannedPath() {
        return (List<Direction>) directions.values();
    }

    public void setPlannedPath(List<Direction> directions) {
        this.directions.clear();
        for (Direction d : directions) {
            this.directions.put(directions.listIterator().nextIndex(), d);
        }
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
