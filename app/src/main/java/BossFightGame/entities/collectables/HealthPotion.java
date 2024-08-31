package BossFightGame.entities.collectables;

import BossFightGame.gamesetup.GamePanel;

public class HealthPotion extends Collectable {

    public HealthPotion(GamePanel gp) {
        super(gp, "/collectables/health_potion", 400, 800);
    }

    @Override
    public void applyEffect() {
        gp.getPlayer().heal(25);
    }
}
