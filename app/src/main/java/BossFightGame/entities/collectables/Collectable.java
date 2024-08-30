package BossFightGame.entities.collectables;


import BossFightGame.entities.Entity;
import BossFightGame.gamesetup.GamePanel;

public abstract class Collectable extends Entity {
    GamePanel gp;

    public Collectable(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }
    public abstract void applyEffect();
}
