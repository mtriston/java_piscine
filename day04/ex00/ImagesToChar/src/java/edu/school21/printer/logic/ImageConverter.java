package src.java.edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public static void printImage(File image, char white, char black) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                int pixel = bufferedImage.getRGB(j, i);
                System.out.print(pixel == -1 ? white : black);
            }
            System.out.println();
        }
    }
}