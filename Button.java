import java.awt.*;

public class Button {
    private Graphics2D g;
    private int width, height;
    public int x, y;
    private int aX, aY;
    private Color color, mouseOverColor, clickedColor, textColor, outlineColor;
    private String text;
    private int baseFontSize;

    public Button(int width, int height, int x, int y, Color color, String text, int fontSize){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
        this.baseFontSize = fontSize;
    }

    public void setGraphics(Graphics2D g){
        this.g = g;
    }

    private void setFontSize(int size) {
        g.setFont(new Font("Comfortaa", Font.PLAIN, size));
    }

    /**
     * Renders the button. X and Y is top left corner. Mouse X and Y are for colors.
     */
    public void draw(int mx, int my, boolean click) {
        double hoverScalar = 1.05;
        int outlineThickness = 10;

        int w = (mousedOver(mx, my)) ? (int) Math.round(width * hoverScalar) : width;
        int h = (mousedOver(mx, my)) ? (int) Math.round(height * hoverScalar) : height;

        int oW = w + outlineThickness;
        int oH = h + outlineThickness;

        int xShift = mousedOver(mx, my) ? (width - w) / 2 : 0;
        int yShift = mousedOver(mx, my) ? (height - h) / 2 : 0;

        int xOutline = (width - oW) / 2;
        int yOutline = (height - oH) / 2;

        g.setColor(outlineColor);
        g.fillRect(aX + xOutline, aY + yOutline, oW,  oH);

        if(mousedOver(mx, my) && click) {
            g.setColor(clickedColor);
        } else if (mousedOver(mx, my)) {
            g.setColor(mouseOverColor);
        } else {
            g.setColor(color);
        }

        g.fillRect(aX + xShift, aY + yShift, w,  h);

        if (mousedOver(mx, my)) { setFontSize((int) (baseFontSize * hoverScalar)); } else { setFontSize(baseFontSize); }

        // Get the FontMetrics for the current font
        FontMetrics fm = g.getFontMetrics(g.getFont());

        // Get the width of the string
        int stringWidth = fm.stringWidth(this.text);
        int stringHeight = fm.getHeight();

        g.setColor(textColor);
        int stringX = aX + xShift + (w / 2) - (stringWidth / 2);
        int stringY = aY + yShift + (h / 2) + (stringHeight / 4);

        g.drawString(this.text, stringX, stringY);
    }

    public void setMode(int mode) {
        if(mode == 0) {
            aX = x;
            aY = y;
        } else if (mode == 1) {
            aX = x - (width / 2);
            aY = y - (height / 2);
        }
    }

    /**
     *
     * @return True if mouse is over button, false otherwise.
     */
    public boolean mousedOver(int mx, int my) {
        int yOffset = 0;
        boolean xBoundry = mx > aX && mx < aX + width;
        boolean yBoundry = my > aY + yOffset && my < aY + height + yOffset;

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
    public void setSpecialColors(Color mouseOver, Color clicked, Color outline) {
        this.mouseOverColor = mouseOver;
        this.clickedColor = clicked;
        this.outlineColor = outline;
    }

    /**
     * Sets the text that is drawn when rendered.
     * @param text Display Text
     */
    public void setText(String text){
        this.text = text;
    }
    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public String toString() {
        String mode = aX == x ? "Corner" : "Center";
        return String.format("X: %d, Y: %d, Width: %d, Height: %d, Text: %s, Drawing Mode: %s\nColors: [%s, %s, %s, %s]", x, y, width, height, text, mode, color, mouseOverColor, clickedColor, textColor);
    }
}
