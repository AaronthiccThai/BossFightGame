package BossFightGame.entities;

import BossFightGame.gamesetup.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
    private int x;
    private int y;
    protected GamePanel gp;
    protected Rectangle solidArea;

    public Entity(GamePanel gp) {
        this.gp = gp;
        this.solidArea = new Rectangle();
    }    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.solidArea.x = x; // Update rectangle's x coordinate
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
        this.solidArea.y = y; // Update rectangle's y coordinate
    }

    public int getHitBoxX() {
        return solidArea.x;
    }
    public int getHitBoxY() {
        return solidArea.y;
    }
    public void setHitBoxSize(int width, int height) {
        this.solidArea.width = width;
        this.solidArea.height = height;
    }

    public Rectangle getHitBoxArea() {
        return solidArea;
    }
    // Used for finding image path and adding it to the screen
    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public abstract void draw(Graphics2D g2);

    public abstract void update();
    
}
