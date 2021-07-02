package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class ImageConverter {

    public static void printImage(File image, String white, String black) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);

        ColoredPrinter coloredPrinter = new ColoredPrinter();

        Ansi.BColor whiteColor = Ansi.BColor.valueOf(white);
        Ansi.BColor blackColor = Ansi.BColor.valueOf(black);

        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int pixel = bufferedImage.getRGB(j, i);
                coloredPrinter.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, pixel == -1 ? whiteColor : blackColor);
            }
            System.out.println();
        }
    }
}