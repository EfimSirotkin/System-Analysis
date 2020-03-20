package sample;


import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageProcessor {

    public static final int MAXIMUM_PIXEL_VALUE = 255;

    public static void grayScaleTransform(String picturePath) throws IOException {
        File sourceFile = new File(picturePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);

        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();
        System.out.println(imageHeight+ "x" + imageWidth);

        for (int i = 0; i < imageHeight; ++i)
            for (int j = 0; j < imageWidth; ++j) {
                int pixel = sourceImage.getRGB(j, i);
                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                int average = (r + g + b) / 3;

                pixel = (a << 24) | (average << 16) | (average << 8) | (average);

                sourceImage.setRGB(j, i, pixel);
            }

        File outputFile = new File("F:\\Code\\labsIIPY\\SA3\\src\\sample\\grayscaled.jpg");
        ImageIO.write(sourceImage, "jpg", outputFile);
    }


    public static void getDistributionArray(String imageFilePath) throws IOException {

        File sourceFile = new File(imageFilePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);

        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();

        int numberOfElements = MAXIMUM_PIXEL_VALUE / 10 + 1;
        int[] generalPixelDistributionArray = new int[numberOfElements];
        int[] redPixelDistributionArray = new int[numberOfElements];
        int[] greenPixelDistributionArray = new int[numberOfElements];
        int[] bluePixelDistributionArray = new int[numberOfElements];
        Color pixelColor;

        for (int i = 0; i < imageHeight; ++i)
            for (int j = 0; j < imageWidth; ++j) {
                pixelColor = new Color(sourceImage.getRGB(j, i));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();
                int generalPixel = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

                redPixelDistributionArray[(red) / 10]++;
                greenPixelDistributionArray[(green) / 10]++;
                bluePixelDistributionArray[(blue) / 10]++;
                generalPixelDistributionArray[(generalPixel) / 10]++;
            }

        Main.generalHistogram = HistogramInitializer.initializeHistogram(generalPixelDistributionArray);
        Main.redHistogram = HistogramInitializer.initializeHistogram(redPixelDistributionArray);
        Main.greenHistogram = HistogramInitializer.initializeHistogram(greenPixelDistributionArray);
        Main.blueHistogram = HistogramInitializer.initializeHistogram(bluePixelDistributionArray);

        Main.generalMELabel = new Label("Общее МО = " + ImageProcessor.calculateMathematicalExpectation(generalPixelDistributionArray, imageHeight, imageWidth));
        Main.redMELabel = new Label("Красный МО = " + ImageProcessor.calculateMathematicalExpectation(redPixelDistributionArray, imageHeight, imageWidth));
        Main.greenMELabel = new Label("Зеленый МО = " + ImageProcessor.calculateMathematicalExpectation(greenPixelDistributionArray, imageHeight, imageWidth));
        Main.blueMELabel = new Label("Голубой МО = " + ImageProcessor.calculateMathematicalExpectation(bluePixelDistributionArray, imageHeight, imageWidth));

    }

    public static int calculateMathematicalExpectation(int[] sourceArray, int imageHeight, int imageWidth) {
        double sampleSize = sourceArray.length;
        double mathematicalExpectation = 0;
        for (int i = 0; i < sampleSize; ++i) {
            mathematicalExpectation += sourceArray[i] * (((i+1)*10));
            System.out.println(sourceArray[i]);
        }
        System.out.println(mathematicalExpectation);
        mathematicalExpectation /= imageWidth*imageHeight;
        return (int) mathematicalExpectation;
    }

}
