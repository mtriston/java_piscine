package chase;

import java.awt.Point;

public abstract class AUnit {

    public final Point location;
    protected final IMap map;

    public AUnit(int x, int y, IMap map) {
        location = new Point(x, y);
        this.map = map;
    }

    Point getLocation() {
        return location;
    }

    abstract public Status makeStep();
}