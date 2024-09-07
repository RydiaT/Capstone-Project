import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JPanel implements MouseMotionListener, MouseListener {
    Button testButton = new Button(300, 50, 400, 300, Color.red, "Load Image", 25);
    JTextField textField = new JTextField("\"D:\\2020-Projects\\Java\\School\\Legally Distinct Mon\\Mon Images\\Sparky.png\"");
    int mouseX, mouseY = 0;
    boolean isClicking = false;

    ImageHandler handler = new ImageHandler();

    public GUI() {
        // Set layout to null if you want absolute positioning or a specific layout manager
        setLayout(null);

        // Set JTextField properties and position
        textField.setBounds(250, 200, 300, 30);  // (x, y, width, height)
        add(textField);

        // Add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        setup(g2d);

        drawButtons(g2d);
        add(textField);
        drawHelpText(g2d);

        if(!handler.getLog().isEmpty()) {
            drawLog(g2d);
        }

        if(handler.getImage() != null) {
            drawImage(g2d);
        }
    }

    public void setup(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Enable Anti-Aliasing for shapes
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Enable Anti-Aliasing for text
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // handler.setGraphics(g);
    }

    public void drawButtons(Graphics2D g) {
        testButton.setGraphics(g);
        testButton.setSpecialColors(Color.blue, Color.yellow);
        testButton.setTextColor(Color.orange);
        testButton.setMode(1);
        testButton.draw(mouseX, mouseY, isClicking);
        // print(testButton);
    }

    public void drawHelpText(Graphics2D g) {
        String[] info = new String[]{"Hello!", "Please enter the full path to the image!", "Click the 'Done' Button to get your image processed!", "It will appear as '(original-name)'-2", "in the same folder."};

        g.setColor(Color.black);
        g.setFont(new Font("Comfortta", Font.PLAIN, 20));

        for(int i = 0; i < info.length; i++) {
            g.drawString(info[i], (getWidth() / 2) - (g.getFontMetrics().stringWidth(info[i]) / 2), g.getFontMetrics().getHeight() * (i + 1));
        }
    }

    public void drawLog(Graphics2D g) {
        g.setFont(new Font("Comfortta", Font.PLAIN, 20));
        Color logColor = handler.isError() ? Color.red : Color.green;

        g.setColor(logColor);

        g.drawString(handler.getLog(), (getWidth() / 2) - (g.getFontMetrics().stringWidth(handler.getLog()) / 2), 400);
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(handler.getImage(), 300, 400, this);
    }

    // Mouse Listeners

    @Override
    public void mousePressed(MouseEvent e) {
        if(!isClicking) { isClicking = true; repaint(); }

        // print("Is Clicking: " + isClicking);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // List of avaliable buttons
        Button[] buttons = new Button[]{testButton};
        // Currently clicked button
        int buttonID = -1;

        // Find clicked button
        for(int i = 0; i < buttons.length; i++) {
            if(buttons[i].mousedOver(mouseX, mouseY) && isClicking) { buttonID = i; }
        }

        // Carry out "on click" events.
        switch(buttonID) {
            case 0:
                handler.setFilepath(textField.getText());
                handler.loadImage();
                break;
            default:
                print("No button selected!");
                break;
        }

        isClicking = false;

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        // print("Mouse X: " + mouseX + ", Mouse Y: " + mouseY);

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        // print("Mouse X: " + mouseX + ", Mouse Y: " + mouseY);

        repaint();
    }

    // --------- Unused ---------

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void print(Object o) {
        System.out.println(o);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // if(isClicking) { isClicking = false; repaint(); }

        // print("Is Clicking: " + isClicking);
    }
}
