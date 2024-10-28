import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageHandler {
    private String filePath = "";
    private String fileName = "";
    private String fileType = "";
    private BufferedImage image = null;
    private BufferedImage scaledImage = null;
    private BufferedImage filteredImage = null;
    private String outputLog = "";
    private boolean isError = false;
//    private Graphics2D g;

//    public void setGraphics (Graphics2D g) {
//        this.g = g;
//    }

    public void setFilepath(String filepath) {
        this.filePath = filepath.replace("\"", "");

        int index = 0;

        for(int i = filePath.length() - 1; i >= 0; i--) {
            if(filePath.charAt(i) == '\\') { index = i; break; }
        }

        index++;

        this.fileName = filePath.substring(index);
        this.filePath = filePath.substring(0, index);
        this.fileType = fileName.substring(fileName.lastIndexOf("."));
        this.fileName = fileName.replace(fileType, "");
    }

    public void loadImage() {
        try {
            image = ImageIO.read(new File(filePath + fileName + fileType));
            outputLog = "Image Loaded!";
            isError = false;

            generateScaledImage(this.image,250, 250);
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

    public void generateScaledImage(BufferedImage image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics2D context for the scaled image
        Graphics2D g = newImage.createGraphics();

        // Use smooth scaling (optional, but recommended for better quality)
        g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);

        // Release resources used by the Graphics2D context
        g.dispose();

        scaledImage = newImage;
    }

    private BufferedImage cloneImage(BufferedImage donor) {
        BufferedImage b = new BufferedImage(donor.getWidth(), donor.getHeight(), donor.getType());
        Graphics g = b.getGraphics();
        g.drawImage(donor, 0, 0, null);
        g.dispose();
        return b;
    }
    
    public void greyScale() {
        BufferedImage dummyImage = cloneImage(image);

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                int newPixel = (rgb[1] + rgb[2] + rgb[3]) / 3;
                int newRgb = (rgb[0] << 24) | (newPixel << 16) | (newPixel << 8) | newPixel;

                dummyImage.setRGB(i, j, newRgb);
            }
        }

        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    public void methodOne() {
        int shiftStrength = 10;
        BufferedImage dummyImage = cloneImage(image);

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                rgb[1] += (int) Math.floor(Math.random() * shiftStrength);
                rgb[2] += (int) Math.floor(Math.random() * shiftStrength);
                rgb[3] += (int) Math.floor(Math.random() * shiftStrength);

                capRGB(dummyImage, i, j, rgb);
            }
        }

        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    public void sepia() {
        BufferedImage dummyImage = cloneImage(image);

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                int newRed = (int) (0.593 * rgb[1] + 0.769 * rgb[2] + 0.189 * rgb[3]);
                int newGreen = (int) (0.349 * rgb[1] + 0.686 * rgb[2] + 0.168 * rgb[3]);
                int newBlue  = (int) (0.272 * rgb[1] + 0.534 * rgb[2] + 0.131 * rgb[3]);

                rgb[1] = newRed;
                rgb[2] = newGreen;
                rgb[3] = newBlue;

                capRGB(dummyImage, i, j, rgb);
            }
        }

        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    public void tint(int mode) {
        BufferedImage dummyImage = cloneImage(image);
        double tintStrength = 2;

        if(mode > 2 || mode < 0) { mode = 0; }

        mode++;

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                rgb[mode] = (int) (rgb[mode] * tintStrength);

                capRGB(dummyImage, i, j, rgb);
            }
        }

        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    public void stamp() {
        BufferedImage dummyImage = cloneImage(image);
        double opacity = 0.2;

        Pattern pattern = new Pattern(10, 10);

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                int sX = i % 10;
                int sY = j % 10;

                int[] sRGB = pattern.getPixel(sX, sY);

                int newRed = (int) Math.round(opacity * sRGB[0] + (1 - opacity) * rgb[1]);
                int newGreen = (int) Math.round(opacity * sRGB[1] + (1 - opacity) * rgb[2]);
                int newBlue = (int) Math.round(opacity * sRGB[2] + (1 - opacity) * rgb[3]);

                int[] newRGB = new int[]{newRed, newGreen, newBlue, 255};

                capRGB(dummyImage, i, j, newRGB);
            }
        }
        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    public void stamp(Pattern pattern) {
        BufferedImage dummyImage = cloneImage(image);
        double opacity = 0.1;

        for(int i = 0; i < dummyImage.getWidth(); i++) {
            for(int j = 0; j < dummyImage.getHeight(); j++) {
                int[] rgb = getRGB(dummyImage, i, j);

                int sX = i % pattern.getRows();
                int sY = j % pattern.getCols();

                int[] sRGB = pattern.getPixel(sX, sY);

                int newRed = (int) Math.round(opacity * sRGB[0] + (1 - opacity) * rgb[1]);
                int newGreen = (int) Math.round(opacity * sRGB[1] + (1 - opacity) * rgb[2]);
                int newBlue = (int) Math.round(opacity * sRGB[2] + (1 - opacity) * rgb[3]);

                int[] newRGB = new int[]{newRed, newGreen, newBlue, 255};

                capRGB(dummyImage, i, j, newRGB);
            }
        }
        filteredImage = dummyImage;
        generateScaledImage(dummyImage, 250, 250);
    }

    private void capRGB(BufferedImage dummyImage, int i, int j, int[] rgb) {
        for(int c = 0; c < 3; c++) {
            if (rgb[c] > 255) { rgb[c] = 255; }
        }

        int newRgb = (rgb[3] << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];

        dummyImage.setRGB(i, j, newRgb);
    }

    private int[] getRGB(BufferedImage image, int x, int y) {
        int[] rgba = new int[4];

        int rgb = image.getRGB(x, y);
        rgba[0] = (rgb >> 24) & 0xff;
        rgba[1] = (rgb >> 16) & 0xFF;
        rgba[2] = (rgb >> 8) & 0xFF;
        rgba[3] = rgb & 0xFF;

        return rgba;
    }

    public void saveImage() {
        try {
            File output = new File(filePath + fileName + "-2" + fileType);

            ImageIO.write(filteredImage, fileType.substring(1), output);
            isError = false;
            outputLog = "Image Saved!";
        } catch (IOException e) {
            isError = true;
            outputLog = e.getMessage();
        }

    }

    public int[][] getImageRGB() {
        BufferedImage dummyImage = cloneImage(scaledImage);
        int width = dummyImage.getWidth();
        int height = dummyImage.getHeight();

        // Create a 2D array: [total_pixels][3] for RGB values
        int[][] out = new int[width * height][4];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Extract RGB values for pixel (i, j)
                int[] rgb = getRGB(dummyImage, i, j);

                // Store RGB values in linear index (i * height + j)
                out[j * width + i][0] = rgb[1];
                out[j * width + i][1] = rgb[2];
                out[j * width + i][2] = rgb[3];
            }
        }

        return out;
    }
}
