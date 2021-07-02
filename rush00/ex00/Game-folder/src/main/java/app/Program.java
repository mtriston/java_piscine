package app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import logic.Map;
import logic.Properties;

@Parameters(separators = "=")
class Args {
    @Parameter(names = {"--enemiesCount="}, required = true)
    private int enemiesCount;

}

public class Program {
    public static void main(String[] args) {
        Properties properties = null;
        try {
            properties = Properties.newInstance("/home/mtriston/IdeaProjects/java_piscine/rush00/ex00/Game-folder/src/main/resources/application-production.properties", 10, 3, 20);
        } catch (Exception e) {
            System.err.println("Invalid property file!");
            System.exit(-1);
        }
        Map map = new Map(properties);
        map.generateRandom();
        map.print();
    }
}