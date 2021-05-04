package model;

public class Clock {

    private int clock;
    private boolean hasChanged = false;

    public Clock(int clock) {
        this.clock = clock;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
}
