package model.entities;

import model.Coords;

public class Blockage {

    private Coords position;

    public Blockage(Coords position) {
        this.position = position;
    }

    public Coords getPosition() {
        return position;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }

}
