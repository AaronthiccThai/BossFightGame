package BossFightGame.gamesetup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import BossFightGame.entities.Entity;
import BossFightGame.entities.Player;
import BossFightGame.entities.collectables.Collectable;
import BossFightGame.entities.collectables.HealthPotion;
import BossFightGame.entities.collectables.ManaPotion;
import BossFightGame.entities.enemies.Enemy;
import BossFightGame.entities.enemies.OrcLord;
import BossFightGame.entities.projectiles.Projectile;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;

    // BG
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 16;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private final Color gridColor = Color.decode("#006400"); // Green color for grid lines
    private final Color backgroundColor = Color.black; // Background color

    // SYSTEM
    private KeyBoardHandler keyHandler = new KeyBoardHandler(this);
    private Thread gameThread; 
    private int FPS = 60;
    Player player = new Player(this, keyHandler);
    private UI ui = new UI(this);

    // ENTITIES
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Enemy> bosses = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Collectable> collectables = new ArrayList<>();

    OrcLord orcLord = new OrcLord(this);
    HealthPotion hP = new HealthPotion(this);
    ManaPotion mP = new ManaPotion(this);    
    
    // GAME STATE 
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int gameOverState = 3;
    private final int winGameState = 4;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public int getTileSize() {
        return tileSize;
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setUpGame() {
        gameState = titleState;
    }

    @Override
    public void run() { 
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            // Might change this to make it more simpler
            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext()) {
                Projectile p = iterator.next();
                p.update();
                if (!p.isAlive()) {
                    iterator.remove(); 
                }
            }
            for (Collectable c : collectables) {
                c.update();

            }
            for (Enemy b : bosses) {
                b.update();
            }
        } 
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;  
        if (!bosses.isEmpty()) {
            checkBossDefeated();
        }      
        if (gameState == titleState || gameState == gameOverState || gameState == winGameState) {
            ui.draw(g);
        } else {
            // Draw the background
            drawBG(g);
            player.draw(g);
            for (Enemy e : bosses) {
                e.draw(g);
            }
            for (Projectile p : projectiles) {
                p.draw(g);
            }    
            
            for (Collectable c : collectables) {
                c.draw(g);
            }
            ui.draw(g);
        }

        g.dispose();
    }
    private void drawBG(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the grid lines
        g.setColor(gridColor);
        for (int i = 0; i <= maxScreenRow; i++) {
            g.drawLine(0, i * tileSize, screenWidth, i * tileSize); 
        }
        for (int j = 0; j <= maxScreenCol; j++) {
            g.drawLine(j * tileSize, 0, j * tileSize, screenHeight); 
        }
    }
    public void resetGame() {
        setGameState(getPlayState());
        player.setDefaultValues();
        for (Enemy e : bosses) {
            e.setDefaultValues();
        }
    }

    public void addEntities() {
        entities.add(player);
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public int getGameState() {
        return gameState;
    }
    public void setGameState(int state) {
        this.gameState = state;
    }
    public int getPlayState() {
        return playState;
    }
    public int getPauseState() {
        return pauseState;
    }
    public int getTitleState() {
        return titleState;
    }
    public int getGameOverState() {
        return gameOverState;
    }
    public int getGameWinState() {
        return winGameState;
    }
    public Player getPlayer() {
        return player;
    }

    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    public void removeProjectile(Projectile p) {
        projectiles.remove(p);
    }    
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addCollectables(Collectable c) {
        collectables.add(c);
        entities.add(c);
    }

    public ArrayList<Collectable> getCollectables() {
        return collectables;
    }
    public void addBosses(Enemy e) {
        bosses.add(e);
        entities.add(e);
    }
    public ArrayList<Enemy> getBosses() {
        return bosses;
    }
    public void checkBossDefeated() {
        int check = 0;
        for (Enemy e : bosses) {
            if (e.getCurrentHealth() <= 0) {
                check++;
            }
        }
        if (check == bosses.size()) {
            gameState = winGameState;
        }
    }

}
