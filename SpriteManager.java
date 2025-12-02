/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author hemis
 */
class SpriteManager {
    private static SpriteManager instance;
    private HashMap<String, BufferedImage> sprites;
    private final boolean useSprites;
    
    private SpriteManager() {
        sprites = new HashMap<>();
        useSprites = loadSprites();
    }
    
    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }
    
    private boolean loadSprites() {
        try {
            sprites.put("player", createPlayerSprite());
            sprites.put("crate", createCrateSprite());
            sprites.put("wall", createWallSprite());
            sprites.put("target", createTargetSprite());
            sprites.put("floor", createFloorSprite());
            return true;
        } catch (Exception e) {
            System.err.println("Error loading sprites: " + e.getMessage());
            return false;
        }
    }
    
    private BufferedImage createPlayerSprite() {
        BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Body
        g.setColor(new Color(255, 200, 0));
        g.fillOval(5, 5, 30, 30);
        
        // Outline
        g.setColor(new Color(200, 150, 0));
        g.setStroke(new BasicStroke(2));
        g.drawOval(5, 5, 30, 30);
        
        // Eyes
        g.setColor(Color.BLACK);
        g.fillOval(13, 13, 4, 4);
        g.fillOval(23, 13, 4, 4);
        
        // Smile
        g.drawArc(12, 15, 16, 12, 0, -180);
        
        g.dispose();
        return img;
    }
    
    private BufferedImage createCrateSprite() {
        BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Main box
        g.setColor(new Color(139, 69, 19));
        g.fillRect(2, 2, 36, 36);
        
        // 3D effect - lighter top/left
        g.setColor(new Color(160, 82, 45));
        g.fillRect(2, 2, 36, 4);
        g.fillRect(2, 2, 4, 36);
        
        // 3D effect - darker bottom/right
        g.setColor(new Color(101, 50, 10));
        g.fillRect(2, 34, 36, 4);
        g.fillRect(34, 2, 4, 36);
        
        // Border
        g.setColor(Color.BLACK);
        g.drawRect(2, 2, 36, 36);
        
        // Wood grain lines
        g.setColor(new Color(101, 50, 10));
        g.drawLine(10, 6, 10, 34);
        g.drawLine(20, 6, 20, 34);
        g.drawLine(30, 6, 30, 34);
        
        // X marking
        g.setColor(new Color(80, 40, 10));
        g.setStroke(new BasicStroke(2));
        g.drawLine(12, 12, 28, 28);
        g.drawLine(28, 12, 12, 28);
        
        g.dispose();
        return img;
    }
    
    private BufferedImage createWallSprite() {
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Stone brick pattern
        g.setColor(new Color(100, 100, 100));
        g.fillRect(0, 0, 50, 50);
        
        // Brick details
        g.setColor(new Color(120, 120, 120));
        g.fillRect(2, 2, 20, 20);
        g.fillRect(27, 2, 20, 20);
        g.fillRect(2, 27, 20, 20);
        g.fillRect(27, 27, 20, 20);
        
        // Mortar lines
        g.setColor(new Color(70, 70, 70));
        g.fillRect(0, 23, 50, 4);
        g.fillRect(23, 0, 4, 50);
        
        // Shadow effect
        g.setColor(new Color(80, 80, 80));
        g.drawLine(0, 49, 49, 49);
        g.drawLine(49, 0, 49, 49);
        
        g.dispose();
        return img;
    }
    
    private BufferedImage createTargetSprite() {
        BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Outer ring
        g.setColor(new Color(100, 200, 100, 180));
        g.fillOval(2, 2, 36, 36);
        
        // Middle ring
        g.setColor(new Color(80, 180, 80, 200));
        g.fillOval(8, 8, 24, 24);
        
        // Inner circle
        g.setColor(new Color(60, 160, 60, 220));
        g.fillOval(14, 14, 12, 12);
        
        // Border
        g.setColor(new Color(50, 150, 50));
        g.setStroke(new BasicStroke(2));
        g.drawOval(2, 2, 36, 36);
        
        g.dispose();
        return img;
    }
    
    private BufferedImage createFloorSprite() {
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Floor tile
        g.setColor(new Color(220, 220, 200));
        g.fillRect(0, 0, 50, 50);
        
        // Tile pattern
        g.setColor(new Color(200, 200, 180));
        g.drawRect(1, 1, 47, 47);
        
        g.dispose();
        return img;
    }
    
    public BufferedImage getSprite(String name) {
        return sprites.get(name);
    }
    
    public boolean hasSprites() {
        return useSprites;
    }
}