package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

public class Program {
    public static String imagePath = "/resources/image.bmp";

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Invalid arguments! Expected '. 0");
            System.exit(-1);
        }

        try {
            ImageConverter.printImage(imagePath, args[0].charAt(0), args[1].charAt(0));
        } catch (Exception e) {
            System.err.println("Error! Can't print image.");
            System.err.println(e.getMessage());
        }
    }
}