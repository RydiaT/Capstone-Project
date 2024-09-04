import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JPanel implements MouseMotionListener, MouseListener {
    Button testButton = new Button(300, 100, 400, 300, Color.red, "Hello World!");
    int mouseX, mouseY = 0;
    boolean isClicking = false;

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Enable Anti-Aliasing for shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Enable Anti-Aliasing for text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        testButton.setGraphics(g2d);
        testButton.setSpecialColors(Color.blue, Color.yellow);
        testButton.draw(mouseX, mouseY, isClicking);
    }

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
                testButton.setText("I've been clicked!");
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
