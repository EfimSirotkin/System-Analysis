package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCreator {
    private BufferedImage img;

    public ImageCreator() {
        img = null;
    }
    public BufferedImage ReadImage(File sourceFile) throws IOException {
       BufferedImage testImage = ImageIO.read(sourceFile);
        return testImage;
    }

    public void setImage(BufferedImage sourceImage) {
        img = sourceImage;
    }

    public BufferedImage getImage() {
        return img;
    }

}
