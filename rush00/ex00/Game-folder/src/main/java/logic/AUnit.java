package logic;

import java.awt.Point;

public abstract class AUnit {

    protected final Point location;
    protected final Map map;

    public AUnit(int x, int y, Map map) {
        location = new Point(x, y);
        this.map = map;
    }

    Point getLocation() {
        return location;
    }

    abstract public Status makeStep();
}