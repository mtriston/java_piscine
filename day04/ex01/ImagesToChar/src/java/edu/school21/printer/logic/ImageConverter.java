package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageConverter {

    public static void printImage(String image, char white, char black) throws IOException {
        InputStream in = Objects.requireNonNull(ImageConverter.class.getResourceAsStream(image));
        BufferedImage bufferedImage = ImageIO.read(in);
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