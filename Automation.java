import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class Automation {
    public static void main(String[] args) {
        String path = "Images";

        File folder = new File(path);

        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // Check if the file name ends with common image extensions
                String lowerCaseName = name.toLowerCase();
                return lowerCaseName.endsWith(".png") ||
                        lowerCaseName.endsWith(".jpg") ||
                        lowerCaseName.endsWith(".jpeg") ||
                        lowerCaseName.endsWith(".gif") ||
                        lowerCaseName.endsWith(".bmp");
            }
        };

        String[] imageNames = folder.list(imageFilter);

        assert imageNames != null;

        for (String image : imageNames) {
            System.out.println("Looking At: " + image);

            ImageHandler temp = new ImageHandler();
            temp.setFilepath("Images\\" + image);
            temp.loadImage();
            temp.stamp();
            temp.saveImage("Filtered Images");
        }

        System.out.println("Done. <3");
    }
}
