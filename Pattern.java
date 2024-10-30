public class Pattern {
    private int rows;
    private int cols;
    private int[][] argb;

    public Pattern(int rows, int cols, int[][] argb) {
        this.rows = rows;
        this.cols = cols;
        this.argb = argb;
    }

    public Pattern(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.argb = new int[rows * cols][4];

        for (int i = 0; i < rows * cols; i++) {
            argb[i][0] = 255;

            for (int j = 1; j < 4; j++) {
                argb[i][j] = (int) (Math.random() * 255);
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

        this.argb = temp.getImageRGB();
    }

    public int[][][] toArray() {
        int[][][] out = new int[rows][cols][4];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                for(int k = 0; k < 4; k++) {
                    out[i][j][k] = argb[i * cols + j][k];
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
        return argb;
    }

    public int[] getPixel(int x, int y) {
        int[][][] array = toArray();

        return array[x][y];
    }
}
