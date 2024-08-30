package BossFightGame.entities.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import BossFightGame.gamesetup.GamePanel;

public class Rock extends Projectile {
    GamePanel gp;
    private BufferedImage image;
    public Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
    }

    public void draw(Graphics2D g2) {
        int drawX = getX() * gp.getTileSize();
        int drawY = getY() * gp.getTileSize();
        g2.drawImage(image, drawX, drawY, gp.getTileSize(), gp.getTileSize(), null);        
    }

    public void setDefaultValues() {
        speed = 1;
        damage = 10;
        maxLife = 60;
        life = maxLife;
        alive = false;
        getImage();
    }

    public void getImage() {
        image = setup("/projectiles/rock_down_1", gp.getTileSize(), gp.getTileSize());
    }
}
