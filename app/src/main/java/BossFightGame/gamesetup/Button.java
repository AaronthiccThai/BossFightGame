package BossFightGame.gamesetup;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class Button {
    private String text;
    private Rectangle bounds;
    private Color normalColor = Color.WHITE;
    private Color hoverColor = Color.YELLOW;
    private boolean isHovered = false;

    public Button(String text, int x, int y, int width, int height) {
        this.text = text;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(isHovered ? hoverColor : normalColor);
        g2.drawString(text, bounds.x, bounds.y + bounds.height - 10); // Adjust for text baseline
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public String getText() {
        return text;
    }
}
