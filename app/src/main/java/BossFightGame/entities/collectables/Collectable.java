package BossFightGame.entities.collectables;

import BossFightGame.entities.Entity;
import BossFightGame.entities.Player;
import BossFightGame.gamesetup.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class Collectable extends Entity {
    protected GamePanel gp;
    private boolean exist;
    private BufferedImage img;
    private int spawnTimer;
    private int minSpawnInterval;
    private int maxSpawnInterval;

    public Collectable(GamePanel gp, String imagePath, int minSpawnInterval, int maxSpawnInterval) {
        super(gp);
        this.gp = gp;
        this.minSpawnInterval = minSpawnInterval;
        this.maxSpawnInterval = maxSpawnInterval;
        this.exist = false;
        this.img = setup(imagePath, gp.getTileSize(), gp.getTileSize());
        gp.addCollectables(this);
        System.out.println(gp.getCollectables());
        resetSpawnTimer();
    }

    public void update() {
        if (exist) {
            onOverlap(gp.getPlayer());
        } else {
            spawnTimer--;
            if (spawnTimer <= 0) {
                setRandomPosition();
                exist = true;
                resetSpawnTimer();
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (exist) {
            g2.drawImage(img, getX(), getY(), gp.getTileSize(), gp.getTileSize(), null);
        }
    }

    public abstract void applyEffect();

    public void onOverlap(Entity entity) {
        if ((getX() / gp.getTileSize()) == entity.getX() && (getY() / gp.getTileSize()) == entity.getY()) {
            if (entity instanceof Player) {
                applyEffect();
                exist = false;
            }
        }
    }

    protected void setRandomPosition() {
        ArrayList<int[]> emptySpots = new ArrayList<>();
        
        for (int x = 0; x < gp.getMaxScreenRow(); x++) {
            for (int y = 0; y < gp.getMaxScreenCol(); y++) {
                if (!isOccupied(x, y)) {
                    emptySpots.add(new int[]{x, y});
                }
            }
        }

        if (!emptySpots.isEmpty()) {
            Random rand = new Random();
            int[] selectedSpot = emptySpots.get(rand.nextInt(emptySpots.size()));
            setX(selectedSpot[0] * gp.getTileSize());
            setY(selectedSpot[1] * gp.getTileSize());
        }
    }

    private boolean isOccupied(int x, int y) {
        for (Entity entity : gp.getEntities()) {
            if (entity.getX() == x && entity.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void resetSpawnTimer() {
        Random rand = new Random();
        spawnTimer = rand.nextInt(maxSpawnInterval - minSpawnInterval + 1) + minSpawnInterval;
    }
}
