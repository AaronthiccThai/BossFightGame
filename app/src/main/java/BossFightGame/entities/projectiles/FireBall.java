package BossFightGame.entities.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import BossFightGame.gamesetup.GamePanel;

public class FireBall extends Projectile {
    private BufferedImage up1,down1,left1, right1;
    private GamePanel gp;
    public FireBall(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    private void getImage() {
        up1 = setup("/projectiles/fireball_up_1", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/projectiles/fireball_down_1", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/projectiles/fireball_left_1", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/projectiles/fireball_right_1", gp.getTileSize(), gp.getTileSize());
    }

    public void setDefaultValues() {
        speed = 1;
        damage = 10;
        maxLife = 30;
        life = maxLife;
        alive = false;
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
        int drawX = getX() * gp.getTileSize();
        int drawY = getY() * gp.getTileSize();
        g2.drawImage(image, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);


        // Draw hitbox -- for DEBUG
        // int hitBoxDrawX = drawX + solidArea.x;
        // int hitBoxDrawY = drawY + solidArea.y;
        // g2.setColor(Color.RED);
        // g2.drawRect(hitBoxDrawX, hitBoxDrawY, solidArea.width, solidArea.height);     
    }
}
