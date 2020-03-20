package sample;


import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

        File outputFile = new File("src/sample/grayscaled.jpg");
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

        int generalME = ImageProcessor.calculateMathematicalExpectation(generalPixelDistributionArray, imageHeight, imageWidth);
        int redME = ImageProcessor.calculateMathematicalExpectation(redPixelDistributionArray, imageHeight, imageWidth);
        int greenME = ImageProcessor.calculateMathematicalExpectation(greenPixelDistributionArray, imageHeight, imageWidth);
        int blueME = ImageProcessor.calculateMathematicalExpectation(bluePixelDistributionArray, imageHeight, imageWidth);

        Main.generalMELabel = new Label("Выборочное среднее = " + generalME);
        Main.redMELabel = new Label("Выборочное среднее(R) = " + redME);
        Main.greenMELabel = new Label("Выборочное среднее(G) = " + greenME);
        Main.blueMELabel = new Label("Выборочное среднее(B) = " + blueME);

        int generalRMS =  ImageProcessor.calculateRootMeanSquare(generalPixelDistributionArray, generalME, imageWidth, imageHeight*255);
        int redRMS = ImageProcessor.calculateRootMeanSquare(redPixelDistributionArray, redME, imageWidth, imageHeight*255);
        int greenRMS = ImageProcessor.calculateRootMeanSquare(greenPixelDistributionArray, greenME, imageWidth, imageHeight*255);
        int blueRMS = ImageProcessor.calculateRootMeanSquare(bluePixelDistributionArray, blueME, imageWidth, imageHeight*255);

        Main.generalRMSLabel = new Label("Среднее квадратическое отклонение = " + generalRMS);
        Main.redRMSLabel = new Label("Среднее квадратическое отклонение(R) = " + redRMS);
        Main.greenRMSLabel = new Label("Среднее квадратическое отклонение(G) = " + greenRMS);
        Main.blueRMSLabel = new Label("Среднее квадратическое отклонение(B) = " + blueRMS);

        int generalModa = ImageProcessor.calculateModa(generalPixelDistributionArray, imageWidth, imageHeight);
        int redModa = ImageProcessor.calculateModa(redPixelDistributionArray, imageWidth, imageHeight);
        int greenModa = ImageProcessor.calculateModa(greenPixelDistributionArray, imageWidth, imageHeight);
        int blueModa = ImageProcessor.calculateModa(bluePixelDistributionArray, imageWidth, imageHeight);

        Main.generalModaLabel = new Label("Мода = " + generalModa);
        Main.redModaLabel = new Label("Мода(R) = " + redModa);
        Main.greenModaLabel = new Label("Мода(G) = " + greenModa);
        Main.blueModaLabel = new Label("Мода(B) = " + blueModa);

        int generalMedian = ImageProcessor.calculateMedian(generalPixelDistributionArray, imageWidth, imageHeight);
        int redMedian = ImageProcessor.calculateMedian(redPixelDistributionArray, imageWidth, imageHeight);
        int greenMedian = ImageProcessor.calculateMedian(greenPixelDistributionArray, imageWidth, imageHeight);
        int blueMedian = ImageProcessor.calculateMedian(bluePixelDistributionArray, imageWidth, imageHeight);

        Main.generalMedianLabel = new Label("Медиана = " + generalMedian);
        Main.redMedianLabel = new Label("Медиана = " + redMedian);
        Main.greenMedianLabel = new Label("Медиана = " + greenMedian);
        Main.blueMedianLabel = new Label("Медиана = " + blueMedian);
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

    public static int calculateRootMeanSquare(int[] sourceArray, int mathematicalExpectation, int imageWidth, int imageHeight) {
        double rootMeanSquare = 0;
        int arraySize = sourceArray.length;
        for(int i = 0; i < arraySize; ++i) {
            rootMeanSquare += Math.pow((sourceArray[i] - mathematicalExpectation), 2);
        }
        rootMeanSquare /= imageWidth*imageHeight;
        return (int) Math.sqrt(rootMeanSquare);
    }

    public static int calculateModa(int[] sourceArray, int imageWidth, int imageHeight) {
        int maximumFreqValue = 0;
        int modaIndex = 0;

        for(int i = 0; i < sourceArray.length; ++i) {
            if(sourceArray[i] > maximumFreqValue) {
                maximumFreqValue = sourceArray[i];
                modaIndex = i;
            }
        }
        return (modaIndex)*10 + (int) (Math.random() * 10);
    }

    public static int calculateMedian(int[] sourceArray, int imageWidth, int imageHeight) {
        int tempSum = 0;
        int arraySize = sourceArray.length;
        int average = imageHeight * imageWidth / 2;
        int delta;
        int medianIndex = 0;
        int lowerboundary = 0;

        for(int i = 0; i < arraySize; ++i) {
            tempSum += sourceArray[i];
            if(tempSum >= average) {
                medianIndex = i;
                break;
            }
        }
        tempSum -= sourceArray[medianIndex];
        delta = average - tempSum;

        return (medianIndex*10 +  delta*10 / sourceArray[medianIndex]);
    }

}
