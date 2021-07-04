package chase;

import java.awt.Point;

public abstract class AUnit {

    public final Point location;
    protected final IMap map;
    public boolean isPlayer;

    public AUnit(int x, int y, IMap map, boolean isPlayer) {
        location = new Point(x, y);
        this.map = map;
        this.isPlayer = isPlayer;
    }

    Point getLocation() {
        return location;
    }

    abstract public Status makeStep();
}