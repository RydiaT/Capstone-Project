import java.awt.*;

public class Button {
    private Graphics2D g;
    private int width, height;
    public int x, y;
    private Color color, mouseOverColor, clickedColor;
    private String text;

    public Button(int width, int height, int x, int y, Color color, String text){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
    }

    public void setGraphics(Graphics2D g){
        this.g = g;
        g.setFont(new Font("Comfortaa", Font.PLAIN, 32));
    }

    /**
     * Renders the button. X and Y is top left corner. Mouse X and Y are for colors.
     */
    public void draw(int mx, int my, boolean click) {
        // Get the FontMetrics for the current font
        FontMetrics fm = g.getFontMetrics(g.getFont());

        // Get the width of the string
        int stringWidth = fm.stringWidth(this.text);
        int stringHeight = fm.getHeight();

        if(mousedOver(mx, my) && click) {
            g.setColor(clickedColor);
        } else if (mousedOver(mx, my)) {
            g.setColor(mouseOverColor);
        } else {
            g.setColor(color);
        }

        g.fillRect(x, y, width, height);

        g.setColor(Color.black);
        int stringX = x + (width / 2) - (stringWidth / 2);
        int stringY = y + (height / 2) + (stringHeight / 4);
        g.drawString(this.text, stringX, stringY);
    }

    /**
     *
     * @return True if mouse is over button, false otherwise.
     */
    public boolean mousedOver(int mx, int my) {
        int yOffset = 30;
        boolean xBoundry = mx > x && mx < x + width;
        boolean yBoundry = my > y + yOffset && my < y + height + yOffset;

        return xBoundry && yBoundry;
    }

    /**
     *
     * @return True if clicked, false otherwise.
     */
    private boolean clicked() {
        return false;
    }

    /**
     * For changing the mouse over and clicked colors.
     * @param mouseOver Color when mouse hovering
     * @param clicked Color when clicked
     */
    public void setSpecialColors(Color mouseOver, Color clicked) {
        this.mouseOverColor = mouseOver;
        this.clickedColor = clicked;
    }

    /**
     * Sets the text that is drawn when rendered.
     * @param text Display Text
     */
    public void setText(String text){
        this.text = text;
    }
}
