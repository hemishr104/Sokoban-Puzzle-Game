/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skokabangame;

/**
 *
 * @author hemis
 */
interface Movable {

    // Move object to a new position implementation differs per class (polymorphism)
    void moveTo(int x, int y);

    // Gives current position required for all movable actors
    Position getPosition();

    // Checks if something is allowed to move into specific map coordinates 
    // each class can decide its own movement logic (classic polymorphic behavior)
    boolean canMoveTo(int x, int y, int[][] map);
}