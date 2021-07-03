package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import edu.school21.printer.logic.ImageConverter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Parameters(separators = "=")
class Args {
    @Parameter(names = {"--white"}, required = true)
    String white;

    @Parameter(names = {"--black"}, required = true)
    String black;
}

public class Program {

    public static String imagePath = "/resources/image.bmp";

    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));
        Args arguments = new Args();

        try {
            JCommander.newBuilder().addObject(arguments).build().parse(args);
        } catch (Exception e) {
            System.err.println("Invalid arguments! Example: $ java -jar images-to-chars-printer.jar --white=RED --black=GREEN");
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        try {
            ImageConverter.printImage(imagePath, arguments.white, arguments.black);
        } catch (Exception e) {
            System.err.println("Error! Can't print image.");
            System.err.println(e.getMessage());
        }
    }
}