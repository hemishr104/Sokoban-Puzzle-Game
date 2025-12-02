/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

/**
 *
 * @author hemis
 */
/**
 * Position class represents a coordinate (x, y) on the game grid.
 * Demonstrates OOP concepts:
 * Encapsulation: x and y are private, access controlled via getters and setters.
 * Abstraction: exposes operations like move(), equals(), and hashCode() without revealing internal representation.
 */
class Position {
    private int x; // X coordinate (encapsulated)
    private int y; // Y coordinate (encapsulated)
    
    // Constructor to create a position at specific coordinates
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Copy constructor to duplicate another Position object
    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    // Getters (encapsulation)
    public int getX() { return x; }
    public int getY() { return y; }
    
    // Setters (encapsulation)
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    // Move the position by a delta (dx, dy)
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    // Compare positions for equality
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    // Generate hash code for using Position in collections like HashSet or HashMap
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
        return hash;
    }
}