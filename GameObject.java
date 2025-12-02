/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

import java.awt.Graphics;

/**
 *
 * @author hemis
 */
/**
 * Base class for all objects placed on the Sokoban map.
 * This class represents **abstraction**: it defines what every game object
 * must have (position, symbol, ability to draw/move) but does NOT define
 * how each one looks or behaves exactly.
 */
abstract class GameObject implements Movable {

    // Position is kept private to the object this is **encapsulation**.
    // Outside classes don't modify its internals directly.
    protected Position position;

    // Symbol used for map representation; also encapsulated inside the class.
    protected char symbol;

    // Constructor initializes an object's shared state.
    // All subclasses inherit this behavior -> **inheritance**
    public GameObject(int x, int y, char symbol) {
        this.position = new Position(x, y);
        this.symbol = symbol;
    }

    // Getter exposes position safely while preserving encapsulation.
    @Override
    public Position getPosition() { 
        return position; 
    }

    // Getter for symbol.
    public char getSymbol() { 
        return symbol; 
    }

    // Each subclass must implement its own drawing behavior.
    // This is **polymorphism**: same method name, different behaviors.
    public abstract void draw(Graphics g, int cellSize);

    // Default movement rule shared across all game objects.
    // Subclasses can override this is  polymorphism.
    @Override
    public boolean canMoveTo(int x, int y, int[][] map) {

        // Boundary check to prevent invalid movement
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return false;
        }

        // Tile with value 1 = wall -> blocked movement
        return map[y][x] != 1;
    }
}