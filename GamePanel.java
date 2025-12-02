/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author hemis
 */
/**
 * GamePanel handles all drawing and input for the Sokoban game.
 * This class acts as the *view* and *controller* in the program.
 * It demonstrates:
 * - Inheritance: extends JPanel to become a drawable UI component
 * - Polymorphism: overrides paintComponent() from JPanel
 * - Encapsulation: hides level state internally inside this panel
 * - Abstraction: exposes only the methods needed by the UI system
 */
class GamePanel extends JPanel implements KeyListener {

    // Fixed pixel size of each grid cell
    private static final int CELL_SIZE = 50;

    // Current Level object — encapsulated so other classes cannot directly modify it
    private Level currentLevel;

    // Tracks which level the player is playing
    private int levelNumber;
    
    public GamePanel() {
        levelNumber = 1;
        currentLevel = new Level(levelNumber);   // Composition: GamePanel *has a* Level
        
        // Compute panel dimensions based on map size (abstraction)
        int panelWidth = currentLevel.getWidth() * CELL_SIZE;
        int panelHeight = currentLevel.getHeight() * CELL_SIZE + 80; // Extra space for UI only
        
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(new Color(245, 245, 220));
        setFocusable(true);          // Needed for key input
        addKeyListener(this);        // GamePanel is its own KeyListener (inheritance + polymorphism)
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // paintComponent is overridden -> polymorphism
        try {
            drawLevel(g);
            drawUI(g);
        } catch (Exception e) {
            g.setColor(Color.RED);
            g.drawString("Error rendering: " + e.getMessage(), 10, 20);
        }
    }
    
    private void drawLevel(Graphics g) {
        // Retrieve map data from Level (encapsulation)
        int[][] map = currentLevel.getMap();
        SpriteManager spriteManager = SpriteManager.getInstance();
        
        // Loop through grid tiles
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int px = x * CELL_SIZE;
                int py = y * CELL_SIZE;
                
                // Draw floor (background tile)
                BufferedImage floorSprite = spriteManager.getSprite("floor");
                if (floorSprite != null) {
                    g.drawImage(floorSprite, px, py, CELL_SIZE, CELL_SIZE, null);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                }
                
                // Draw grid lines for clarity
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(px, py, CELL_SIZE, CELL_SIZE);
                
                // Draw walls
                if (map[y][x] == 1) {
                    BufferedImage wallSprite = spriteManager.getSprite("wall");
                    if (wallSprite != null) {
                        g.drawImage(wallSprite, px, py, CELL_SIZE, CELL_SIZE, null);
                    } else {
                        g.setColor(new Color(100, 100, 100));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(px, py, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
        
        // Draw all target tiles
        BufferedImage targetSprite = spriteManager.getSprite("target");
        for (Position target : currentLevel.getTargets()) {
            int x = target.getX() * CELL_SIZE;
            int y = target.getY() * CELL_SIZE;
            
            if (targetSprite != null) {
                int offsetX = (CELL_SIZE - targetSprite.getWidth()) / 2;
                int offsetY = (CELL_SIZE - targetSprite.getHeight()) / 2;
                g.drawImage(targetSprite, x + offsetX, y + offsetY, null);
            } else {
                g.setColor(new Color(100, 200, 100));
                g.fillOval(x + 10, y + 10, CELL_SIZE - 20, CELL_SIZE - 20);
            }
        }
        
        // Draw crates using polymorphism — each crate draws itself
        for (Crate crate : currentLevel.getCrates()) {
            crate.draw(g, CELL_SIZE);
        }
        
        // Draw player (also polymorphism from subclass implementation)
        currentLevel.getPlayer().draw(g, CELL_SIZE);
    }
    
    private void drawUI(Graphics g) {
        int uiY = currentLevel.getHeight() * CELL_SIZE + 5;
        int panelWidth = currentLevel.getWidth() * CELL_SIZE;
        
        // UI background strip
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, uiY, panelWidth, 75);
        
        // Level and moves counter
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("LEVEL: " + levelNumber + " / 5", 10, uiY + 25);
        g.drawString("MOVES: " + currentLevel.getMoves(), 10, uiY + 50);
        
        // Controls text display (just cosmetic)
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int controlX = panelWidth / 2 - 50;
        g.drawString("↑↓←→ or WASD to Move", controlX, uiY + 20);
        g.drawString("R - Reset Level", controlX, uiY + 35);
        g.drawString("N - Next Level", controlX, uiY + 50);
        
        // Goal description
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.setColor(new Color(100, 255, 100));
        g.drawString("GOAL: Push all crates to green targets!", 10, uiY + 68);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // KeyListener override ⇒ polymorphism
        
        try {
            boolean moved = false;
            
            // Movement keys delegate movement logic to Level (encapsulation)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> moved = currentLevel.movePlayer(0, -1);
                case KeyEvent.VK_W -> moved = currentLevel.movePlayer(0, -1);
                case KeyEvent.VK_DOWN -> moved = currentLevel.movePlayer(0, 1);
                case KeyEvent.VK_S -> moved = currentLevel.movePlayer(0, 1);
                case KeyEvent.VK_LEFT -> moved = currentLevel.movePlayer(-1, 0);
                case KeyEvent.VK_A -> moved = currentLevel.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> moved = currentLevel.movePlayer(1, 0);
                case KeyEvent.VK_D -> moved = currentLevel.movePlayer(1, 0);
                case KeyEvent.VK_R -> resetLevel();
                case KeyEvent.VK_N -> nextLevel();
            }
            
            // Win check
            if (moved && currentLevel.isLevelComplete()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Level Complete! Moves: " + currentLevel.getMoves(),
                    "Victory!",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            repaint(); // Trigger redraw
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Resets the current level but keeps the same level number
    private void resetLevel() {
        currentLevel = new Level(levelNumber);
        
        // Recompute dimensions for the new level layout
        int panelWidth = currentLevel.getWidth() * CELL_SIZE;
        int panelHeight = currentLevel.getHeight() * CELL_SIZE + 80;
        
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        
        // Resize the window (UI consistency)
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null);
        }
        
        revalidate();
        repaint();
    }
    
    // Moves to next level in range 1–5
    private void nextLevel() {
        levelNumber = (levelNumber % 5) + 1;
        resetLevel();
    }
    
    // Unused KeyListener methods
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
}