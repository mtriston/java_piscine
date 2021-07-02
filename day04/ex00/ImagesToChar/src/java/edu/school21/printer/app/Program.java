package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

import java.io.File;

public class Program {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.println("Invalid arguments! Expected 'it.bmp . 0");
            System.exit(-1);
        }

        try {
            ImageConverter.printImage(new File(args[0]), args[1].charAt(0), args[2].charAt(0));
        } catch (Exception e) {
            System.err.println("Error! Can't print image.");
            System.err.println(e.getMessage());
        }
    }
}