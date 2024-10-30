import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JPanel implements MouseMotionListener, MouseListener {
    // Colors
    Color backgroundColor = new Color(94, 102, 102);
    Color textColor = new Color(245, 228, 152);
    Color buttonColor1 = new Color(100, 100, 100);
    Color buttonColor2 = new Color(75, 75, 75);
    Color buttonColor3 = new Color(120, 120, 120);
    Color buttonColor4 = new Color(185, 144, 49);
    Color logError = new Color(250, 165, 165);
    Color logSuccess = new Color(204, 247, 171);
    Color checkered1 = new Color(56, 56, 56);
    Color checkered2 = new Color(84, 84, 84);

    // GUI Elements

    // Main Screen
    Button loadImage = new Button(300, 50, 160, 500, buttonColor1, "Load Image", 25);
    Button processImage = new Button(300, 50, 620, 500, buttonColor1, "Process Image", 25);
    Button saveImage = new Button(150, 50, 390, 500, buttonColor1, "Save Image", 20);
    Button optionsMenu = new Button(300, 50, 160, 150, buttonColor1, "Options", 25);

    JTextField imageInput = new JTextField("");

    // Options
    Button setPatternImage = new Button(300, 50, 160, 500, buttonColor1, "Set Pattern Image", 25);
    String[] filterText = new String[]{"Random Pattern", "Image Pattern", "Random Shift", "Greyscale", "Sepia", "Red Shift", "Blue Shift", "Green Shift"};
    Radio filters = new Radio(20, 350, 350, 25, textColor, filterText);
    JTextField patternInput = new JTextField("");
    Button back = new Button(300, 50, 160, 150, buttonColor1, "Back", 25);

    // Thingamajigs
    int mouseX, mouseY = 0;
    boolean isClicking = false;
    int state = 0;
    String pattern = "";

    ImageHandler handler = new ImageHandler();

    public GUI() {
        // Set layout to null if you want absolute positioning or a specific layout manager
        setLayout(null);

        // Set JTextField properties and position
        imageInput.setBounds(10, 420, 300, 30); // (x, y, width, height)
        patternInput.setBounds(30, 300, 300, 30);

        // Add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        setup(g2d);

        if(state == 0) {
            drawButtons(g2d);
            add(imageInput);
            drawHelpText(g2d);

            if(!handler.getLog().isEmpty()) {
                drawLog(g2d);
            }

            drawGrid(g2d);

            if(handler.getImage() != null) {
                drawImage(g2d);
            }
        } else if (state == 1) {
            drawSettings(g2d);
            add(patternInput);
        }


    }

    public void drawSettings(Graphics2D g) {
        filters.setGraphics(g);
        filters.setColors(checkered1, textColor);

        filters.draw();

        Button[] buttons = new Button[]{setPatternImage, back};

        for(Button button : buttons) {
            button.setGraphics(g);
            button.setSpecialColors(buttonColor2, buttonColor3, buttonColor4);
            button.setMode(1);
            button.setTextColor(textColor);
            button.draw(mouseX, mouseY, isClicking);
        }
    }

    public void setup(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Enable Anti-Aliasing for shapes
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Enable Anti-Aliasing for text
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // handler.setGraphics(g);

        remove(imageInput);
        remove(patternInput);
    }

    public void drawButtons(Graphics2D g) {
        Button[] buttons = new Button[]{loadImage, processImage, saveImage, optionsMenu};

        for(Button button : buttons) {
            button.setGraphics(g);
            button.setSpecialColors(buttonColor2, buttonColor3, buttonColor4);
            button.setMode(1);
            button.setTextColor(textColor);
            button.draw(mouseX, mouseY, isClicking);
        }
        // print(testButton);
    }

    public void drawHelpText(Graphics2D g) {
        String[] info = new String[]{"Hello!", "Please enter the full path to the image!", "Click Load, then Process, and then Save.", "It will appear as '(original-name)'-2", "in the same folder."};

        g.setColor(textColor);
        g.setFont(new Font("Comfortta", Font.PLAIN, 20));

        for(int i = 0; i < info.length; i++) {
            g.drawString(info[i], (getWidth() / 2) - (g.getFontMetrics().stringWidth(info[i]) / 2), g.getFontMetrics().getHeight() * (i + 1));
        }
    }

    public void drawLog(Graphics2D g) {
        g.setFont(new Font("Comfortta", Font.PLAIN, 20));
        Color logColor = handler.isError() ? logError : logSuccess;

        g.setColor(logColor);

        g.drawString(handler.getLog(), (getWidth() / 2) - (g.getFontMetrics().stringWidth(handler.getLog()) / 2), 450);
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(handler.getImage(), (getWidth() / 2) - 125, 150, this);
    }

    public void drawGrid(Graphics2D g) {
        int size = 250;
        int rows = 10;
        int s = size / rows;
        int initialX = (getWidth() / 2) - 125;
        int initialY = 150;

        boolean flipFlop = false;

        for(int i = 0; i < rows; i++) {
            flipFlop = i % 2 == 0;

            for(int j = 0; j < rows; j++) {
                Color color = flipFlop ? checkered1 : checkered2;

                g.setColor(color);
                g.fillRect(initialX + (s * i), initialY + (s * j), s, s);

                flipFlop = !flipFlop;
            }
        }
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
        Button[] buttons = new Button[0];

        if (state == 0) {
            buttons = new Button[]{loadImage, processImage, saveImage, optionsMenu};
        } else if (state == 1) {
            buttons = new Button[]{setPatternImage, back};
        }
        // Currently clicked button
        int buttonID = -1;

        // Find clicked button
        for(int i = 0; i < buttons.length; i++) {
            if(buttons[i].mousedOver(mouseX, mouseY) && isClicking) { buttonID = i; }
        }

        // Carry out "on click" events.
        switch(buttonID) {
            case 0:
                if(state == 0) {
                    handler.setFilepath(imageInput.getText());
                    handler.loadImage();
                } else if(state == 1) {
                    pattern = patternInput.getText();
                }
                break;
            case 1:
                if(state == 0) {
                    switch(filters.getSelected()) {
                        case 0:
                            handler.stamp();
                            break;
                        case 1:
                            Pattern stamp = new Pattern(10, 10, pattern);
                            handler.stamp(stamp);
                            break;
                        case 2:
                            handler.methodOne();
                            break;
                        case 3:
                            handler.greyScale();
                            break;
                        case 4:
                            handler.sepia();
                            break;
                        case 5:
                            handler.tint(0);
                            break;
                        case 6:
                            handler.tint(1);
                            break;
                        case 7:
                            handler.tint(2);
                            break;
                        default:
                            print("No Filter Set!");
                            break;
                    }
                } else if(state == 1) {
                    state = 0;
                }
                break;
            case 2:
                handler.saveImage();
                break;
            case 3:
                state = 1;
                break;
            default:
                print("No button selected!");
                break;
        }

        if(state == 1) {
            int selected = filters.getHoveredCircle(e.getX(), e.getY());

            if(selected != -1 && isClicking) {
                filters.setSelected(selected);
            }
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

    @Override
    public void mouseReleased(MouseEvent e) {
        // if(isClicking) { isClicking = false; repaint(); }

        // print("Is Clicking: " + isClicking);
    }

    public static void print(Object o) {
        System.out.println(o);
    }
}
