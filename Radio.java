import java.awt.*;

public class Radio {
    private Graphics2D g;
    private int r;
    private int x;
    private int y;
    private int spacing;
    private String[] options;
    private Color baseColor, selectedColor, textColor;
    int selected = -1;

    public Radio(int r, int x, int y, int spacing, Color color, String[] options) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.spacing = spacing;
        this.baseColor = color;
        this.options = options;
    }

    public void setColors(Color selectedColor, Color textColor) {
        this.selectedColor = selectedColor;
        this.textColor = textColor;
    }

    public void draw() {
        setFontSize(20);

        for (int i = 0; i < options.length; i++) {
            g.setColor(baseColor);

            // Calculate the vertical position for each radio button
            int yPosition = y + i * spacing;

            // Draw the radio button circle
            g.fillOval(x, yPosition - r / 2, r, r);  // Center the circle vertically

            if(selected == i) {
                g.setColor(selectedColor);
                g.fillOval(x + 2, (yPosition - r / 2) + 2, r - 5, r - 5);
            }

            // Draw the option text next to the radio button
            g.setColor(textColor);
            g.drawString(options[i], x + r + spacing, yPosition + spacing / 4);
        }
    }

    public int getHoveredCircle(int mouseX, int mouseY) {
        for (int i = 0; i < options.length; i++) {
            int yPosition = y + i * spacing;

            // Calculate the center of the current circle
            int centerX = x + r / 2;

            // Check if the mouse is within the circle using the distance formula
            double distance = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - yPosition, 2));
            if (distance <= (double) r / 2) {
                return i;  // Return the index of the hovered circle
            }
        }

        return -1;  // No circle is hovered
    }

    public String getOption(int index) {
        return options[index];
    }

    public void setSelected(int index) {
        selected = index;
    }

    public int getSelected() {
        return selected;
    }

    private void setFontSize(int size) {
        g.setFont(new Font("Comfortaa", Font.PLAIN, size));
    }
    public void setGraphics(Graphics2D g){
        this.g = g;
    }
}
