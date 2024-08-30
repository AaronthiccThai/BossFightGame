package BossFightGame.entities.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import BossFightGame.gamesetup.GamePanel;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class OrcLord extends Enemy {
    GamePanel gp;
    private int maxHealth;
    private int currentHealth;
    private BufferedImage left1;
    Font maruMonica;
    private List<Rectangle> hitBoxes;


    public OrcLord(GamePanel gp) {
        super(gp);
        this.gp = gp;
        hitBoxes = new ArrayList<>();
        setHitBoxSize(gp.getTileSize(), gp.getTileSize());
        setDefaultValues();
        getImage();  
        setFont();
    }

    public void setFont() {
        try {
            InputStream input = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setDefaultValues() {
        // Sets the hitbox and position
        setX(15);
        setY(8);
        setHitBoxes();
        this.maxHealth = 1000;
        this.currentHealth = maxHealth;         
    }
    public void setHitBoxes() {
        int tileSize = gp.getTileSize();
        int newX = getX() - 1;
        // Hitboxes are quite weird
        hitBoxes.add(new Rectangle(newX * tileSize, (getY() + -1) * tileSize, tileSize, tileSize));
        hitBoxes.add(new Rectangle(newX * tileSize , getY() * tileSize, tileSize, tileSize));
        hitBoxes.add(new Rectangle((getX() + 1) * tileSize, getY() * tileSize, tileSize, tileSize));
        hitBoxes.add(new Rectangle(newX * tileSize, (getY() + 1) * tileSize, tileSize, tileSize));
        hitBoxes.add(new Rectangle((getX() + 1) * tileSize, (getY() + 1) * tileSize, tileSize, tileSize));
    }
    public void getImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/enemies/orc_attack_left_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Draw Orc Lord
        int drawX = (getX() - 1) * gp.getTileSize();
        int drawY = getY() * gp.getTileSize();
        int scaleX = 6;
        int scaleY = 4;
        int scaledWidth = gp.getTileSize() * scaleX;
        int scaledHeight = gp.getTileSize() * scaleY;
        int adjustedDrawX = drawX - (scaledWidth - gp.getTileSize()) / 2;
        int adjustedDrawY = drawY - (scaledHeight - gp.getTileSize()) / 2;
        g2.drawImage(left1, adjustedDrawX, adjustedDrawY, scaledWidth, scaledHeight, null);

        // Draw hitbox -- for DEBUG
        // g2.setColor(Color.RED);
        // for (Rectangle hitBox : hitBoxes) {
        //     g2.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        // } 

        // Draw Health Bar
        int barWidth = gp.getTileSize() * 4; 
        int barHeight = 10;
        int barX = (gp.getWidth() - barWidth) / 2;
        int barY = 40; 
        float healthPercentage = (float) currentHealth / maxHealth;
        int healthBarWidth = (int) (barWidth * healthPercentage);

        g2.setColor(Color.GRAY);
        g2.fillRect(barX, barY, barWidth, barHeight);

        g2.setColor(Color.RED);
        g2.fillRect(barX, barY, healthBarWidth, barHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);

        // Draw boss name
        String bossName = "Gorgrim Skullcrusher the Orc Lord";
        int textX = barX + (barWidth - g2.getFontMetrics().stringWidth(bossName)) / 2;
        int textY = barY - 5; // Position the text slightly above the health bar
        g2.setColor(Color.WHITE);
        g2.drawString(bossName, textX, textY);  

    }
    @Override
    public void update() {
        // TODO
    }
    @Override
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0; // Need to transition to gameover state
        }
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }
}
