package BossFightGame.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import BossFightGame.entities.projectiles.FireBall;
import BossFightGame.entities.projectiles.Projectile;
import BossFightGame.gamesetup.GamePanel;
import BossFightGame.gamesetup.KeyBoardHandler;
import java.awt.Color;

public class Player extends Entity {
    private GamePanel gp;
    private KeyBoardHandler keyHandler;

    private BufferedImage up1, down1, left1, right1;
    private String direction;
    private int speed; // This controls how many frames it takes to move
    private int frameCounter; // Counts the number of frames since the last move

    // PLAYER STATS
    private int maxHealth;
    private int currentHealth;
    private int maxMana;
    private int currentMana;
    private int manaRegenerationRate;
    private int manaRegenCounter; 
    private int manaRegenThreshold;  
    private Projectile projectile;
    private int shotCounter;
    private int damage;

    public Player(GamePanel gp, KeyBoardHandler keyHandler) {
        super(gp);
        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
        setHitBoxSize(gp.getTileSize(), gp.getTileSize());
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
    }

    public void setDefaultValues() {
        setX(0);
        setY(8);
        this.speed = 10;
        this.frameCounter = 0;
        this.damage = 50;
        this.currentHealth = maxHealth;
        this.maxMana = 50;
        this.currentMana = maxMana;
        this.manaRegenerationRate = 2;
        this.manaRegenCounter = 0;
        this.manaRegenThreshold = 30;
        direction = "down";
        projectile = new FireBall(gp);
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (currentHealth > 0) {}
        frameCounter++;
        shotCounter++;
        if (frameCounter >= speed) {
            int newX = getX();
            int newY = getY();

            if (keyHandler.getUpKey()) {
                direction = "up";
                newY--; // Move up by 1 row
            } else if (keyHandler.getDownKey()) {
                direction = "down";
                newY++; // Move down by 1 row
            } else if (keyHandler.getRightKey()) {
                direction = "right";
                newX++; // Move right by 1 column
            } else if (keyHandler.getLeftKey()) {
                direction = "left";
                newX--; // Move left by 1 column
            }

            // Ensures the player stays on the grid
            newX = Math.max(0, Math.min(newX, gp.getMaxScreenCol() - 1));
            newY = Math.max(0, Math.min(newY, gp.getMaxScreenRow() - 1));

            // Update player's position and hitbox
            setX(newX);
            setY(newY);

            frameCounter = 0; // Reset the frame counter after moving
        }
        if (keyHandler.getShotKey() && !projectile.isAlive() && shotCounter >= 30 && currentMana >= 25) {
            projectile.set(getX(), getY(), direction, true, this);
            gp.addProjectile(projectile);
            currentMana -= 25; // Reduce mana on shot
            shotCounter = 0;
        }

        regenerateMana(); // Regenerate mana over time
    }

    private void regenerateMana() {
        manaRegenCounter++;
        if (manaRegenCounter >= manaRegenThreshold) {
            if (currentMana < maxMana) {
                currentMana += manaRegenerationRate;
                if (currentMana > maxMana) {
                    currentMana = maxMana;
                }
            }
            manaRegenCounter = 0; 
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }

        // Convert tile-based position to pixel position for drawing
        int drawX = getX() * gp.getTileSize();
        int drawY = getY() * gp.getTileSize();

        g2.drawImage(image, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);

        // Draw the health bar above the player
        int barWidth = gp.getTileSize();
        int barHeight = 5;
        int barX = drawX;
        int barY = drawY - barHeight - 5; // Position the bar above the player

        float healthPercentage = (float) currentHealth / maxHealth;
        int healthBarWidth = (int) (barWidth * healthPercentage);

        // Draw the background of the health bar (gray)
        g2.setColor(Color.GRAY);
        g2.fillRect(barX, barY, barWidth, barHeight);

        // Draw the current health bar (green)
        g2.setColor(Color.GREEN);
        g2.fillRect(barX, barY, healthBarWidth, barHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);

        // Draw the mana bar below the health bar
        barY += barHeight + 2; // Position the mana bar below the health bar

        float manaPercentage = (float) currentMana / maxMana;
        int manaBarWidth = (int) (barWidth * manaPercentage);

        // Draw the background of the mana bar (gray)
        g2.setColor(Color.GRAY);
        g2.fillRect(barX, barY, barWidth, barHeight);

        // Draw the current mana bar (blue)
        g2.setColor(Color.BLUE);
        g2.fillRect(barX, barY, manaBarWidth, barHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);

        // Draw hitbox -- for DEBUG
        int hitBoxDrawX = drawX;
        int hitBoxDrawY = drawY;
        g2.setColor(Color.RED);
        g2.drawRect(hitBoxDrawX, hitBoxDrawY, solidArea.width, solidArea.height);
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
            gp.setGameState(gp.getGameOverState());
        }
    }

    public void heal(int healAmount) {
        currentHealth += healAmount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void regenMana(int manaAmount) {
        currentMana += manaAmount;
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }
    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
}
