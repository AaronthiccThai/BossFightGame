package BossFightGame.gamesetup;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class TileView extends JLabel {
    TileView(Color color, int tileSize) {
        setPreferredSize(new Dimension(tileSize, tileSize));
        setOpaque(true);
        setBackground(color);
    }
}