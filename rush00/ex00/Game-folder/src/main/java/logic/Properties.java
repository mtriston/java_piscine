package logic;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

class CharConverter implements IStringConverter<Character> {
    @Override
    public Character convert(String s) {
        if (s.isEmpty())
            return ' ';
        return s.charAt(0);
    }
}

@Parameters(separators = "=")
public class Properties {
    private final int size;
    private final int enemyCount;
    private final int wallsCount;
    @Parameter(names = {"enemy.char"}, converter = CharConverter.class, required = true)
    private char enemyChar;
    @Parameter(names = {"player.char"}, converter = CharConverter.class, required = true)
    private char playerChar;
    @Parameter(names = {"wall.char"}, converter = CharConverter.class, required = true)
    private char wallChar;
    @Parameter(names = {"goal.char"}, converter = CharConverter.class, required = true)
    private char goalChar;
    @Parameter(names = {"empty.char"}, converter = CharConverter.class, required = true)
    private char emptyChar;
    @Parameter(names = {"enemy.color"},required = true)
    private String enemyColor;
    @Parameter(names = {"player.color"},required = true)
    private String playerColor;
    @Parameter(names = {"wall.color"},required = true)
    private String wallColor;
    @Parameter(names = {"goal.color"},required = true)
    private String goalColor;
    @Parameter(names = {"empty.color"},required = true)
    private String emptyColor;

    private Properties(int size, int enemyCount, int wallsCount) {
        this.size = size;
        this.enemyCount = enemyCount;
        this.wallsCount = wallsCount;
    }

    public static Properties newInstance(String file, int enemiesCount, int wallsCount, int size) throws IOException {
        Properties properties = new Properties(size, enemiesCount, wallsCount);


        List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(file));
        String[] tokens = lines.toArray(new String[lines.size()]);
        JCommander
                .newBuilder()
                .addObject(properties)
                .build()
                .parse(tokens);

        return properties;
    }

    public int getSize() {
        return size;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public char getPlayerChar() {
        return playerChar;
    }

    public char getWallChar() {
        return wallChar;
    }

    public char getGoalChar() {
        return goalChar;
    }

    public char getEmptyChar() {
        return emptyChar;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public String getEmptyColor() {
        return emptyColor;
    }
}
