/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author hemis
 */
/**
 * Crate object for Sokoban.
 * Represents a movable pushable box on the grid.
 */
class Crate extends GameObject {
    
    // Constructor sets initial grid position + identifies object type as 'C'
    public Crate(int x, int y) {
        super(x, y, 'C');
    }
    
    // Updates crate's position on the grid (logical movement)
    public void moveTo(int x, int y) {
        position.setX(x);   // Move crate horizontally
        position.setY(y);   // Move crate vertically
    }
    
    @Override
    public void draw(Graphics g, int cellSize) {
        // Convert grid position to pixel coordinates
        int x = position.getX() * cellSize;
        int y = position.getY() * cellSize;
        
        // Try to get the crate sprite from the SpriteManager
        BufferedImage sprite = SpriteManager.getInstance().getSprite("crate");
        
        if (sprite != null) {
            // If a sprite exists, draw it centered inside the cell
            int offsetX = (cellSize - sprite.getWidth()) / 2;
            int offsetY = (cellSize - sprite.getHeight()) / 2;
            g.drawImage(sprite, x + offsetX, y + offsetY, null);
            
        } else {
            // Fallback drawing if no sprite is available
            g.setColor(new Color(139, 69, 19));  // Wooden-brown color
            g.fill3DRect(x + 5, y + 5, cellSize - 10, cellSize - 10, true);
            
            // Outline + X marking for visual clarity
            g.setColor(Color.BLACK);
            g.drawRect(x + 5, y + 5, cellSize - 10, cellSize - 10);
            g.drawLine(x + 10, y + 10, x + cellSize - 10, y + cellSize - 10);
            g.drawLine(x + cellSize - 10, y + 10, x + 10, y + cellSize - 10);
        }
    }
}