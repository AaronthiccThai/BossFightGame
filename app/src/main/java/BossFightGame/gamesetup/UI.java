package BossFightGame.gamesetup;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage img;
    private Button playButton;
    private Button quitButton;
    private Button retryButton; 
    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream input = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize buttons in constructor
        int screenWidth = gp.getMaxScreenCol() * gp.getTileSize();
        int buttonWidth = 150;
        int buttonHeight = 60;

        playButton = new Button("Play", (screenWidth - buttonWidth) / 2, 500, buttonWidth, buttonHeight);
        quitButton = new Button("Quit", (screenWidth - buttonWidth) / 2, 600, buttonWidth, buttonHeight);
        retryButton = new Button("Retry", screenWidth - buttonWidth - 20, 20, buttonWidth, buttonHeight);
        // Add mouse listener for clicks
        gp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseHover(e.getX(), e.getY());
            }
        });
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        if (gp.getGameState() == gp.getTitleState()) {
            try {
                drawTitleScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (gp.getGameState() == gp.getPlayState()) {
            drawPlayerScreen();
        }
        if (gp.getGameState() == gp.getPauseState()) {
            drawPauseScreen();
        }
    }

    private void drawTitleScreen() throws IOException {
        g2.setColor(Color.BLACK);

        int screenWidth = gp.getMaxScreenCol() * gp.getTileSize();
        int screenHeight = gp.getMaxScreenRow() * gp.getTileSize();
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = "A Boss Fight Game";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int textHeight = g2.getFontMetrics().getHeight();

        int x = (screenWidth - textWidth) / 2;
        int y = textHeight + 50;

        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Load and draw the image
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        // Scale factors
        int scaledWidth = imgWidth * 15;
        int scaledHeight = imgHeight * 15; 

        x = (screenWidth - scaledWidth) / 2; // Center the scaled image horizontally
        y += textHeight + 50; // Position the image below the title
        g2.drawImage(img, x, y, scaledWidth, scaledHeight, null);

        // Draw buttons
        playButton.draw(g2);
        quitButton.draw(g2);
    }

    private void drawPauseScreen() {
        String text = "PAUSE";
    
        // Set a larger font size for the pause text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F)); // Adjust the size to your preference
    
        // Calculate the position to draw the text in the center
        int x = gp.getMaxScreenCol() * gp.getTileSize() / 2;
        int y = gp.getMaxScreenRow() * gp.getTileSize() / 2;
    
        // Measure the width of the text to center it properly
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int textHeight = g2.getFontMetrics().getHeight();
    
        // Adjust x to center the text
        x -= textWidth / 2;
        y += textHeight / 4; // Adjust to ensure vertical centering (it's usually half of the font size)
    
        // Draw the pause text
        g2.setColor(Color.WHITE); // Set the text color
        g2.drawString(text, x, y);
    }
    

    private void drawPlayerScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F)); 
        retryButton.draw(g2);
    }
    private void handleMouseClick(int mouseX, int mouseY) {
        if (playButton.contains(mouseX, mouseY)) {
            // Start the game
            gp.setGameState(gp.getPlayState());
        }
        if (quitButton.contains(mouseX, mouseY)) {
            // Quit the game
            System.exit(0);
        }
        if (retryButton.contains(mouseX, mouseY)) {
            // Retry the game or restart level
            gp.resetGame();
        }        
    }

    private void handleMouseHover(int mouseX, int mouseY) {
        playButton.setHovered(playButton.contains(mouseX, mouseY));
        quitButton.setHovered(quitButton.contains(mouseX, mouseY));
        retryButton.setHovered(retryButton.contains(mouseX, mouseY));

    }
}

