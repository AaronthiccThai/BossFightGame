package BossFightGame.entities.collectables;

import BossFightGame.gamesetup.GamePanel;

public class ManaPotion extends Collectable {

    public ManaPotion(GamePanel gp) {
        super(gp, "/collectables/mana_potion", 300, 600);
    }

    @Override
    public void applyEffect() {
        gp.getPlayer().regenMana(25);
    }
}
