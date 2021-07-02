package app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import logic.Map;
import logic.Properties;

@Parameters(separators = "=")
class Args {
    @Parameter(names = {"--enemiesCount"}, required = true)
    public int enemiesCount;
    @Parameter(names = {"--wallsCount"}, required = true)
    public int wallsCount;
    @Parameter(names = {"--size"}, required = true)
    public int size;
}

public class Program {

    public static String propFile = "/target/classes/application-production.properties";

    public static void main(String[] args) {

        Args arg = new Args();
        try {
            JCommander.newBuilder().addObject(arg).build().parse(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        Properties properties = null;
        try {
            properties = Properties.newInstance(System.getProperty("user.dir") + propFile,
                            arg.enemiesCount, arg.wallsCount, arg.size);
        } catch (Exception e) {
            System.err.println("Invalid property file!");
            System.exit(-1);
        }

        Map map = new Map(properties);
        map.generateRandom();
        map.print();
    }
}