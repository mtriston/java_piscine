package logic;

import java.awt.*;
import java.util.Scanner;

public class Player extends AUnit {

    private static final int UP = 8;
    private static final int DOWN = 2;
    private static final int RIGHT = 6;
    private static final int LEFT = 4;
    private static final int CLOSE = 9;
    private final Scanner scanner = new Scanner(System.in);
    private final Point goal;

    public Player(int x, int y, Map map, Point goal) {
        super(x, y, map);
        this.goal = goal;
    }

    @Override
    public Status makeStep() {
        if (!scanner.hasNextInt()) {
            scanner.next();
            return Status.EGAIN;
        }
        int command = scanner.nextInt();
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
                return Status.LOST;
            default:
                return Status.EGAIN;
        }
        if (!map.isCanGo(newX, newY))
            return Status.EGAIN;
        if (map.isEmpty(newX, newY)) {
            map.moveUnit(this, newX, newY);
            return Status.CONTINUE;
        }
        if (map.isUnit(newX, newY))
            return Status.LOST;
        return Status.WIN;
    }
}