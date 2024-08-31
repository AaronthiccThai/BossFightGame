package BossFightGame.entities.enemies;

import BossFightGame.entities.Entity;
import BossFightGame.gamesetup.GamePanel;

public abstract class Enemy extends Entity{
    GamePanel gp;

    public Enemy(GamePanel gp) {
        super(gp);
        this.gp = gp;
        gp.addBosses(this);
    }

    public abstract void takeDamage(int damage);

    public abstract int getCurrentHealth();

    public abstract void setDefaultValues();
    
    public abstract int getDamage();
}
