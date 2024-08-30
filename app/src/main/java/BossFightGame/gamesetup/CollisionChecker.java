package BossFightGame.gamesetup;

import java.awt.Rectangle;

import BossFightGame.entities.Entity;
import BossFightGame.entities.projectiles.Projectile;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public boolean checkCollision(Entity entity, Projectile projectile) {
        // Get the hitboxes of both the entity and the projectile
        // This is probably not correct hitbox (projectile most likely needa set its hitbox)
        Rectangle entityHitBox = entity.getHitBoxArea(); // OR THE GETHITBOX AREA IS WRONG
        Rectangle projectileHitBox = projectile.getHitBoxArea();

        
        if (entityHitBox.x == projectileHitBox.x && entityHitBox.y == projectileHitBox.y) {
            return true;
        }
        // Check if the two rectangles intersect (collision occurs)
        return false;
    }
}   
