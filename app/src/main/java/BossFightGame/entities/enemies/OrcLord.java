package BossFightGame.entities.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import BossFightGame.entities.projectiles.Rock;
import BossFightGame.gamesetup.GamePanel;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class OrcLord extends Enemy {
    GamePanel gp;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private BufferedImage left1;
    Font maruMonica;
    private List<Rectangle> hitBoxes;
    private boolean alive;
    private int shootDelay = 20; 
    private int shootCounter = 0; 
    private int currentRow = 0;    
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
        this.damage = 20;
        this.maxHealth = 1000;
        this.currentHealth = maxHealth;    
        this.alive = true;     
    }
    public void setHitBoxes() {
        int tileSize = gp.getTileSize();
        int newX = getX();
        // Hitboxes are quite weird
        hitBoxes.add(new Rectangle(newX * tileSize, (getY()) * tileSize, tileSize, tileSize));
        // hitBoxes.add(new Rectangle(newX * tileSize , getY() * tileSize, tileSize, tileSize));
        // hitBoxes.add(new Rectangle((getX() + 1) * tileSize, getY() * tileSize, tileSize, tileSize));
        // hitBoxes.add(new Rectangle(newX * tileSize, (getY() + 1) * tileSize, tileSize, tileSize));
        // hitBoxes.add(new Rectangle((getX() + 1) * tileSize, (getY() + 1) * tileSize, tileSize, tileSize));
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
        if (alive) {
            // Draw Orc Lord
            int drawX = (getX()) * (gp.getTileSize() - 1);
            int drawY = getY() * gp.getTileSize();
            int scaleX = 3;
            int scaleY = 1;
            int scaledWidth = gp.getTileSize() * scaleX;
            int scaledHeight = gp.getTileSize() * scaleY;
            int adjustedDrawX = drawX - (scaledWidth - gp.getTileSize()) / 2;
            int adjustedDrawY = drawY - (scaledHeight - gp.getTileSize()) / 2;
            g2.drawImage(left1, adjustedDrawX, adjustedDrawY, scaledWidth, scaledHeight, null);

            // Draw hitbox -- for DEBUG
            g2.setColor(Color.RED);
            for (Rectangle hitBox : hitBoxes) {
                g2.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
            } 

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
        } else {

        }
    }
    @Override
    public void update() {
        if (alive) {
            // Increment the shoot counter
            shootCounter++;
    
            // If it's time to shoot the next projectile
            if (shootCounter >= shootDelay) {
                if (currentHealth >= 666) {
                    shootRockInRow(currentRow);
                } else if (currentHealth >= 333) {
                    shootDelay = 15;
                    shootRockInRow(currentRow); 
                    shootRockInRow(gp.getMaxScreenRow() - currentRow - 1); 
                } else {
                    if (currentRow % 2 != 0) {
                        shootDelay = 40; 

                        // Shoot all odd rows
                        for (int i = 1; i < gp.getMaxScreenRow(); i += 2) {
                            shootRockInRow(i);
                        }
                    }
                    shootDelay = 20;
                    shootRockInRow(currentRow); 
                    shootRockInRow(gp.getMaxScreenRow() - currentRow - 1); 


                }
                currentRow++; // Move to the next row
                shootCounter = 0; // Reset the counter
    
                // If we've shot all rows, reset to the first row
                if (currentRow >= gp.getMaxScreenRow()) {
                    currentRow = 0;
                }
            }
        }
    }
    
    @Override
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
            alive = false;
        }
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void shootRockInRow(int row) {
        Rock rock = new Rock(gp);
        int startX = getX(); // OrcLord's X position
        int startY = row; // The specified row
        String direction = "left";
        boolean alive = true;
    
        // Use the set() method to initialize the projectile
        rock.set(startX, startY, direction, alive, this);
    
        gp.addProjectile(rock);
    }

    public void shootRockInColumn(int col) {
        for (int row = 0; row < gp.getMaxScreenRow(); row++) {
            Rock rock = new Rock(gp);
            int startX = col; // The specified column
            int startY = row; // Start at the top and go down the rows
            String direction = "down";
            boolean alive = true;
    
            // Use the set() method to initialize the projectile
            rock.set(startX, startY, direction, alive, this);
    
            gp.addProjectile(rock);
        }
    }   
    @Override
    public int getDamage() {
        return damage;
    }
}



