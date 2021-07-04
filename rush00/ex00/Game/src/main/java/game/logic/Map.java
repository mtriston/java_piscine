package game.logic;

import chase.*;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map implements IMap {

    private static final String CLEAR_CONSOLE = "\033[H\033[2J";
    private final Properties props;
    private final char[][] map;
    private final Random random;
    private final List<AUnit> units;

    public Map(Properties properties) {
        this.props = properties;
        map = new char[props.getSize()][props.getSize()];
        random = new Random();
        units = new ArrayList<>();
    }

    public List<AUnit> generateRandom() {

        while (true) {
            int x, y;
            Point goal;
            AUnit player;

            clearMap();
            for (int i = 0; i < props.getWallsCount(); ++i) {
                x = randInt();
                y = randInt();
                if (map[x][y] != props.getEmptyChar()) {
                    --i;
                } else {
                    map[x][y] = props.getWallChar();
                }
            }
            while (true) {
                x = randInt();
                y = randInt();
                if (map[x][y] == props.getEmptyChar()) {
                    map[x][y] = props.getGoalChar();
                    goal = new Point(randInt(), randInt());
                    break;
                }
            }
            while (true) {
                x = randInt();
                y = randInt();
                if (map[x][y] == props.getEmptyChar()) {
                    map[x][y] =props.getPlayerChar();
                    player = new Player(x, y, this, goal, true);
                    units.add(player);
                    break;
                }
            }
            for (int i = 0; i < props.getEnemyCount(); ++i) {
                x = randInt();
                y = randInt();
                if (map[x][y] != props.getEmptyChar()) {
                    --i;
                } else {
                    map[x][y] = props.getEnemyChar();
                    units.add(new Enemy(x, y, this, player, false));
                }
            }
            try {
                LeeAlgorithm.BFS(this, player.location.x, player.location.y, goal.x, goal.y);
                break;
            } catch (Exception ignored) {
            }
        }
        return units;
    }

    public void print() {

        if (!props.getProfile().equalsIgnoreCase("dev")) {
            System.out.print(CLEAR_CONSOLE);
            System.out.flush();
        }

        ColoredPrinter coloredPrinter = new ColoredPrinter();

        Ansi.BColor playerColor = Ansi.BColor.valueOf(props.getPlayerColor());
        Ansi.BColor enemyColor = Ansi.BColor.valueOf(props.getEnemyColor());
        Ansi.BColor wallColor = Ansi.BColor.valueOf(props.getWallColor());
        Ansi.BColor goalColor = Ansi.BColor.valueOf(props.getGoalColor());
        Ansi.BColor emptyColor = Ansi.BColor.valueOf(props.getEmptyColor());


        int height = props.getSize();
        int width = props.getSize();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int pixel = map[j][i];
                if (pixel == props.getPlayerChar())
                    coloredPrinter.print(props.getPlayerChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, playerColor);
                else if (pixel == props.getWallChar())
                    coloredPrinter.print(props.getWallChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, wallColor);
                else if (pixel == props.getGoalChar())
                    coloredPrinter.print(props.getGoalChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, goalColor);
                else if (pixel == props.getEnemyChar())
                    coloredPrinter.print(props.getEnemyChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, enemyColor);
                else
                    coloredPrinter.print(props.getEmptyChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, emptyColor);
            }
            System.out.println();
        }
    }

    private int randInt() {
        return Math.abs(random.nextInt() % props.getSize());
    }

    private void clearMap() {
        char empty = props.getEmptyChar();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                map[j][i] = empty;
            }
        }
    }

    @Override
    public void moveUnit(Player unit, int newX, int newY) {
        map[unit.location.x][unit.location.y] = props.getEmptyChar();
        unit.location.x = newX;
        unit.location.y = newY;
        map[unit.location.x][unit.location.y] = props.getPlayerChar();
    }

    @Override
    public void moveUnit(Enemy unit, int newX, int newY) {
        map[unit.location.x][unit.location.y] = props.getEmptyChar();
        unit.location.x = newX;
        unit.location.y = newY;
        map[unit.location.x][unit.location.y] = props.getEnemyChar();
    }

    public boolean isCanGo(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map.length && !isWall(x, y);
    }

    public boolean isEmpty(int x, int y) {
        return map[x][y] == props.getEmptyChar();
    }

    public boolean isWall(int x, int y) {
        return map[x][y] == props.getWallChar();
    }

    public boolean isUnit(int x, int y) {
        return isEnemy(x, y) || isPlayer(x, y);
    }

    @Override
    public boolean isEnemy(int x, int y) {
        return map[x][y] == props.getEnemyChar();
    }

    @Override
    public boolean isPlayer(int x, int y) {
        return map[x][y] == props.getPlayerChar();
    }

    public int getSize(){
        return props.getSize();
    }
}
