public class Pattern {
    private int rows;
    private int cols;
    private int[][] rgb;

    public Pattern(int rows, int cols, int[][] rgb) {
        this.rows = rows;
        this.cols = cols;
        this.rgb = rgb;
    }

    public Pattern(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.rgb = new int[rows * cols][3];

        for (int i = 0; i < rows * cols; i++) {
            for (int j = 0; j < 3; j++) {
                rgb[i][j] = (int) (Math.random() * 255);
            }
        }
    }

    public Pattern(int rows, int cols, String filepath) {
        this.rows = rows;
        this.cols = cols;
        ImageHandler temp = new ImageHandler();
        temp.setFilepath(filepath);

        temp.loadImage();
        temp.generateScaledImage(temp.getImage(), rows, cols);

        this.rgb = temp.getImageRGB();
    }

    public int[][][] toArray() {
        int[][][] out = new int[rows][cols][3];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                for(int k = 0; k < 3; k++) {
                    out[i][j][k] = rgb[i * cols + j][k];
                }
            }
        }

        return out;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int[][] getRaw() {
        return rgb;
    }

    public int[] getPixel(int x, int y) {
        int[][][] array = toArray();

        return array[x][y];
    }
}
