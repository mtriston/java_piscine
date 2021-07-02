package logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.util.Random;

public class Map {

    private final Properties props;
    private final char[][] map;
    private final Random random;

    public Map(Properties properties) {
        this.props = properties;
        map = new char[props.getSize()][props.getSize()];
        random = new Random();
    }

    public void generateRandom() {
        int x, y;

        clearMap();
        map[randInt()][randInt()] = props.getPlayerChar();

        while (true) {
            x = randInt();
            y = randInt();
            if (map[x][y] == props.getEmptyChar()) {
                map[x][y] = props.getGoalChar();
                break;
            }
        }

        for (int i = 0; i < props.getWallsCount(); ++i) {
            x = randInt();
            y = randInt();
            if (map[x][y] != props.getEmptyChar()) {
                --i;
            } else {
                map[x][y] = props.getWallChar();
            }
        }

        for (int i = 0; i < props.getEnemyCount(); ++i) {
            x = randInt();
            y = randInt();
            if (map[x][y] != props.getEmptyChar()) {
                --i;
            } else {
                map[x][y] = props.getEnemyChar();
            }
        }
    }

    public void print() {
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
}
