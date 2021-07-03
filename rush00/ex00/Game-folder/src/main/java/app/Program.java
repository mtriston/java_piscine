package app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import logic.Game;
import logic.Map;
import logic.Properties;
import java.io.IOException;

class  IllegalParametersException extends IllegalArgumentException {
}

@Parameters(separators = "=")
class Args {
    @Parameter(names = {"--enemiesCount"}, required = true)
    public int enemiesCount;
    @Parameter(names = {"--wallsCount"}, required = true)
    public int wallsCount;
    @Parameter(names = {"--size"}, required = true)
    public int size;
    @Parameter(names = {"--profile"})
    public String profile;
}

public class Program {

    private static String buildPropFileName(String profile) {
        if (profile == null)
            profile = "production";
        return "/application-" + profile + ".properties";
    }

    public static void main(String[] args) {

        Args arg = new Args();
        try {
            JCommander.newBuilder().addObject(arg).build().parse(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        if (arg.size * arg.size <= arg.enemiesCount + arg.wallsCount + 2)
            throw new IllegalParametersException();

        Properties properties = null;
        try {
            properties = Properties.newInstance(buildPropFileName(arg.profile),
                            arg.enemiesCount, arg.wallsCount, arg.size);
        } catch (Exception e) {
            System.err.println("Invalid property file!");
            System.exit(-1);
        }

        Game game = new Game(properties);
        game.run();
    }
}