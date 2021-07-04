package chase;

import java.awt.*;
import java.util.Scanner;

public class Player extends AUnit {

    private static final String UP = "w";
    private static final String DOWN = "s";
    private static final String RIGHT = "d";
    private static final String LEFT = "a";
    private static final String CLOSE = "9";
    private final Scanner scanner = new Scanner(System.in);
    private final Point goal;

    public Player(int x, int y, IMap map, Point goal, boolean isPlayer) {
        super(x, y, map, isPlayer);
        this.goal = goal;
    }

    @Override
    public Status makeStep() {
        if (isSurrounded())
            return Status.LOSE;
        String command = scanner.next().toLowerCase();
        int newX = location.x;
        int newY = location.y;

        switch (command) {
            case (UP):
                --newY;
                break;
            case (DOWN):
                ++newY;
                break;
            case (LEFT):
                --newX;
                break;
            case (RIGHT):
                ++newX;
                break;
            case (CLOSE):
                return Status.LOSE;
            default:
                return Status.AGAIN;
        }
        if (!map.isCanGo(newX, newY))
            return Status.AGAIN;
        if (map.isEmpty(newX, newY)) {
            map.moveUnit(this, newX, newY);
            return Status.CONTINUE;
        }
        if (map.isEnemy(newX, newY))
            return Status.LOSE;
        return Status.WIN;
    }

    private boolean isSurrounded() {
        return !map.isCanGo(location.x + 1, location.y) && !map.isCanGo(location.x - 1, location.y) &&
                !map.isCanGo(location.x, location.y - 1) && !map.isCanGo(location.x + 1, location.y + 1);
    }
}