import javax.swing.*;

public class main {
    public static void main(String[] args) {
        // Create the main window (JFrame)
        JFrame frame = new JFrame("My Window");
        GUI panel = new GUI();

        // Set default close operation (exit the application when the window is closed)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);

        // Set the window size (width x height)
        frame.setSize(800, 600);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the window visible
        frame.setVisible(true);
    }

    public static void print(Object o) {
        System.out.println(o);
    }
}
