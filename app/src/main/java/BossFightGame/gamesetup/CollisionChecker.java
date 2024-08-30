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
        System.out.println("THIS IS ENTITY HITBOX " + entityHitBox.x + " "  + entityHitBox.y);
        Rectangle projectileHitBox = projectile.getHitBoxArea();
        System.out.println("THIS IS PROJECTILE HITBOX " + projectileHitBox.x + " "  + projectileHitBox.y);

        
        if (entityHitBox.x == projectileHitBox.x && entityHitBox.y == projectileHitBox.y) {
            return true;
        }
        // Check if the two rectangles intersect (collision occurs)
        return false;
    }
}   
