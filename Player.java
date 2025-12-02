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
 * Player class represents the player in the Sokoban game.
 * Demonstrates OOP concepts:
 * Inheritance: Player extends GameObject, inheriting position and symbol.
 * Encapsulation: position is protected in GameObject; accessed via getters/setters.
 * Polymorphism: overrides draw() from GameObject to provide custom rendering.
 *  Abstraction: implements Movable behaviors and abstract draw() method without exposing implementation details.
 */
class Player extends GameObject {

    // Constructor to create a Player at a specific position
    public Player(int x, int y) {
        super(x, y, 'P'); // Call to parent constructor (inheritance)
    }

    // Moves the player to a new position (encapsulation of position state)
    public void moveTo(int x, int y) {
        position.setX(x);
        position.setY(y);
    }

    // Draws the player on the game grid (polymorphism: override of abstract method)
    @Override
    public void draw(Graphics g, int cellSize) {
        int x = position.getX() * cellSize;
        int y = position.getY() * cellSize;

        // Try to get the sprite from the SpriteManager
        BufferedImage sprite = SpriteManager.getInstance().getSprite("player");
        if (sprite != null) {
            // Draw sprite centered in the cell
            int offsetX = (cellSize - sprite.getWidth()) / 2;
            int offsetY = (cellSize - sprite.getHeight()) / 2;
            g.drawImage(sprite, x + offsetX, y + offsetY, null);
        } else {
            // Fallback drawing if no sprite is available
            g.setColor(new Color(255, 200, 0));  // Body color
            g.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10); // Body
            g.setColor(Color.BLACK); // Outline
            g.drawOval(x + 5, y + 5, cellSize - 10, cellSize - 10); 
            g.fillOval(x + 15, y + 15, 5, 5); // Eyes
            g.fillOval(x + 30, y + 15, 5, 5);
            g.drawArc(x + 15, y + 20, 20, 15, 0, -180); // Smile
        }
    }
}