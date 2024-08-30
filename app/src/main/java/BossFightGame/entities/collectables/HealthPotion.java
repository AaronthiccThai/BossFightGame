package BossFightGame.entities.collectables;

import BossFightGame.entities.Entity;
import BossFightGame.entities.Player;
import BossFightGame.gamesetup.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class HealthPotion extends Collectable {
    GamePanel gp;
    private boolean exist;
    private BufferedImage img;
    private int spawnTimer;

    private int minSpawnInterval = 300; // minimum time before next spawn
    private int maxSpawnInterval = 600; // maximum time before next spawn
    public HealthPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
    }
    
    @Override
    public void applyEffect() {
        gp.getPlayer().heal(25);
        
    }        
    public void setDefaultValues() {
        exist = false;
        getImage();
        gp.addCollectables(this);

    }
    @Override
    public void draw(Graphics2D g2) {
        // Implement drawing logic here
        if (exist) {
            g2.drawImage(img, getX(), getY(), gp.getTileSize(), gp.getTileSize(), null);
        }    
    }

    @Override
    public void update() {
        if (exist) {
            onOverlap(gp.getPlayer());
        }
        if (!exist) {
            spawnTimer--;
            if (spawnTimer <= 0) {
                setRandomPosition();
                exist = true;
                resetSpawnTimer();
            }
        }
    }

    @Override 
    public void onOverlap(Entity entity) {
        if ((getX() / gp.getTileSize())== entity.getX() && (getY() / gp.getTileSize()) == entity.getY()) {
            System.out.println("X AND Y ARE SAME");
            if (entity instanceof Player) {
                applyEffect();
                exist = false;
                System.out.println("Player collected HealthPotion and healed by 50.");
            }            
        }
    }
    public void getImage() {
        img = setup("/collectables/potion_red", gp.getTileSize(), gp.getTileSize());
    }

    private void setRandomPosition() {
        ArrayList<int[]> emptySpots = new ArrayList<>();
        
        // Traverse the grid to find empty spots
        for (int x = 0; x < gp.getMaxScreenRow(); x++) {
            for (int y = 0; y < gp.getMaxScreenCol(); y++) {
                if (!isOccupied(x, y)) {
                    emptySpots.add(new int[]{x, y});
                }
            }
        }

        // Randomly select an empty spot
        if (!emptySpots.isEmpty()) {
            Random rand = new Random();
            int[] selectedSpot = emptySpots.get(rand.nextInt(emptySpots.size()));
            setX(selectedSpot[0] * gp.getTileSize());
            setY(selectedSpot[1] * gp.getTileSize());
        }
    }

    private boolean isOccupied(int x, int y) {
        // Check if any entity occupies the spot at (x, y)
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
