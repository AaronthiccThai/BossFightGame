package BossFightGame.entities.projectiles;

import BossFightGame.entities.Entity;
import BossFightGame.entities.Player;
import BossFightGame.entities.enemies.Enemy;
import BossFightGame.gamesetup.CollisionChecker;
import BossFightGame.gamesetup.GamePanel;

public abstract class Projectile extends Entity {
    protected int speed;
    protected String direction;
    protected Entity user;
    protected CollisionChecker ck;
    protected int maxLife;
    protected int life; 
    protected boolean alive;
    protected int damage;
    private int moveCounter = 0;
    private int moveDelay = 5;    
    public Projectile(GamePanel gp) {
        super(gp);
        this.ck = new CollisionChecker(gp); 
    
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        setX(worldX);
        setY(worldY);
        setHitBoxSize(worldX, worldY);
        this.direction = direction;
        this.user = user;
        this.life = maxLife;
        this.alive = alive;
    }
    @Override
    public void update() {
        // Collision check and deactivate if necessary
        if (user instanceof Enemy) {
            if (ck.checkCollision(gp.getPlayer(), this)) {
                Enemy e = (Enemy) user;
                gp.getPlayer().takeDamage(e.getDamage()); // Placeholder for damage
                alive = false; // Deactivate after hitting
            }
        }
        // Either the hitbox of the boss is wrong or the projectile is since its shooting way far away. (prob the boss)

        if (user instanceof Player) {
            Player p = (Player) user;
            for (Enemy e : gp.getBosses()) {
                if (ck.checkCollision(e, this)) {
                    e.takeDamage(p.getDamage());
                    alive = false;
                }
            }

        }        
        moveCounter++;
        if (moveCounter >= moveDelay) {
            switch (direction) {
                case "up":
                    setY(getY() - speed);
                    break;
                case "down":
                    setY(getY() + speed);
                    break;
                case "left":
                    setX(getX() - speed);
                    break;
                case "right":
                    setX(getX() + speed);
                    break;
            }
            moveCounter = 0; // Reset the counter
        }

        life--;
        if (life <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public String getDirection() {
        return direction;
    }

}
