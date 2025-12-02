/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package skokabangame;

/**
 *
 * @author hemis
 */
import javax.swing.*;

public class Main extends JFrame {
    private final GamePanel gamePanel; // Encapsulation: internal game panel hidden from outside

    public Main() {
        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Creating the game panel abstraction in action:
        // Main doesn't know how the panel draws sprites or handles input.
        gamePanel = new GamePanel();
        add(gamePanel); // Composition: Main "has" a GamePanel

        pack();  // Auto-sizes window based on panel’s preferred size
        setLocationRelativeTo(null); // Centers the window
        setVisible(true); // Pops the game onto the screen
    }

    public static void main(String[] args) {
        //launches GUI on the event dispatch thread
        SwingUtilities.invokeLater(() -> new Main());
    }
}