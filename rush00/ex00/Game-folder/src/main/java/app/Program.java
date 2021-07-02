package app;

import logic.Properties;

public class Program {
    public static void main(String[] args) {
        System.out.println("Hello");

        try {
            Properties properties = Properties.valueOf("/home/mtriston/IdeaProjects/java_piscine/rush00/ex00/Game-folder/src/main/resources/application-production.properties", 30, 10, 10);
        } catch (Exception e) {
            System.err.println("Invalid properties file!");
            System.exit(-1);
        }
    }
}
