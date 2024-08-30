package BossFightGame.gamesetup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardHandler implements KeyListener {
    private boolean upKey, downKey, rightKey, leftKey;
    private boolean shootKey;
    GamePanel gp;

    public KeyBoardHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (gp.getGameState() == gp.getTitleState()) {

        } else {
            if (keyCode == KeyEvent.VK_F) {
                shootKey = true;
            }
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                upKey = true;
            }
            if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                leftKey = true;
            }
            if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                rightKey = true;
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                downKey = true;
            }
            if (keyCode == KeyEvent.VK_P) {
                if (gp.getGameState() == gp.getPlayState()) {
                    gp.setGameState(gp.getPauseState());
                } else if (gp.getGameState() == gp.getPauseState()) {
                    gp.setGameState(gp.getPlayState());
                }
            }    
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_F) {
            shootKey = false;
        }
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            upKey = false;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            leftKey = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            rightKey = false;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            downKey = false;
        }   
                     
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;        
    }

    public boolean getUpKey() {
        return upKey;
    }

    public boolean getDownKey() {
        return downKey;
    }

    public boolean getRightKey() {
        return rightKey;
    }

    public boolean getLeftKey() {
        return leftKey;
    }
    
    public boolean getShotKey() {
        return shootKey;
    }
}
