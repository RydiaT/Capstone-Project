import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageHandler {
    private String filePath = "";
    private BufferedImage image = null;
    private BufferedImage scaledImage = null;
    private String outputLog = "";
    private boolean isError = false;
//    private Graphics2D g;

//    public void setGraphics (Graphics2D g) {
//        this.g = g;
//    }

    public void setFilepath(String filepath) {
        this.filePath = filepath.replace("\"", "");
    }

    public void loadImage() {
        try {
            image = ImageIO.read(new File(filePath));
            outputLog = "Image Loaded!";
            isError = false;

            generateScaledImage(150, 150);
        } catch (IOException e) {
            isError = true;
            outputLog = e.getMessage();
            System.out.println(outputLog);
            System.out.println(filePath);
        }
    }

    public String getLog() {
        return outputLog;
    }

    public Boolean isError() {
        return isError;
    }

    public BufferedImage getImage() {
        return scaledImage;
    }

    public void generateScaledImage(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics2D context for the scaled image
        Graphics2D g = newImage.createGraphics();

        // Use smooth scaling (optional, but recommended for better quality)
        g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);

        // Release resources used by the Graphics2D context
        g.dispose();

        scaledImage = newImage;
    }
}
