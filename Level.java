/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package skokabangame;

import java.util.ArrayList;

/**
 *
 * @author hemis
 */
/**
 * The Level class represents a single Sokoban level.
 * This class demonstrates **abstraction** because it represents the idea of a level
 * without caring how each object inside it behaves graphically.
 * It also heavily uses **encapsulation** by keeping its data private and exposing
 * only controlled access through methods.
 */
class Level {
    // The 2D integer map layout (walls, empty space, etc.) → encapsulated.
    private int[][] map;

    // Lists storing targets and crates. Encapsulation ensures they are not modified externally.
    private ArrayList<Position> targets;
    private ArrayList<Crate> crates;

    // The single player object in this level.
    private Player player;

    // Track number of moves the player has made.
    private int moves;

    // Which level this is (1–5).
    private int levelNumber;

    // Constructor creating a level based on a level number.
    // This is abstraction Level chooses map data internally.
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.moves = 0;
        this.targets = new ArrayList<>();
        this.crates = new ArrayList<>();
        initializeLevel();
    }

    // Overloaded constructor -> an example of **polymorphism** (method overloading).
    public Level(int levelNumber, int[][] customMap) {
        this.levelNumber = levelNumber;
        this.moves = 0;
        this.targets = new ArrayList<>();
        this.crates = new ArrayList<>();
        this.map = customMap;
        parseMap();
    }

    // Creates one of the five predefined levels.
    private void initializeLevel() {
        // Each map uses tile codes:
        // 0 = empty, 1 = wall, 2 = player, 3 = crate, 4 = target
        map = switch (levelNumber) {
            case 1 -> new int[][] {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 2, 0, 4, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 3, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
            };
            case 2 -> new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 2, 3, 0, 3, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 4, 0, 0, 4, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 3 -> new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 4, 0, 0, 0, 0, 0, 4, 1},
                {1, 0, 1, 1, 0, 0, 1, 0, 1},
                {1, 0, 0, 2, 3, 3, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 4 -> new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 3, 0, 1, 0, 3, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 0, 1, 2, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 3, 0, 0, 0, 0, 1},
                {1, 4, 0, 0, 0, 0, 0, 4, 4, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 5 -> new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 3, 0, 1, 0, 3, 0, 3, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 0, 1, 0, 2, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
                {1, 4, 0, 4, 0, 0, 0, 4, 0, 4, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            default -> new int[][] {{1, 1, 1}, {1, 2, 1}, {1, 1, 1}};
        };
        parseMap();
    }

    // Convert map values into actual objects (Player, Crate, Targets).
    private void parseMap() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {

                switch (map[y][x]) {
                    case 2: // player
                        player = new Player(x, y);
                        map[y][x] = 0; // clear tile
                        break;

                    case 3: // crate
                        crates.add(new Crate(x, y));
                        map[y][x] = 0;
                        break;

                    case 4: // target
                        targets.add(new Position(x, y));
                        map[y][x] = 0;
                        break;
                }
            }
        }
    }

    // Attempts to move the player by dx, dy.
    public boolean movePlayer(int dx, int dy) {
        Position playerPos = player.getPosition();
        int newX = playerPos.getX() + dx;
        int newY = playerPos.getY() + dy;

        // Reject move if out of bounds or into a wall.
        if (!isValidPosition(newX, newY) || map[newY][newX] == 1) {
            return false;
        }

        // Check if a crate is in the way.
        Crate crateAtNewPos = getCrateAt(newX, newY);

        if (crateAtNewPos != null) {
            int crateNewX = newX + dx;
            int crateNewY = newY + dy;

            // Crate can't move into wall, another crate, or outside map.
            if (!isValidPosition(crateNewX, crateNewY) ||
                map[crateNewY][crateNewX] == 1 ||
                getCrateAt(crateNewX, crateNewY) != null) {
                return false;
            }

            // Move the crate.
            crateAtNewPos.moveTo(crateNewX, crateNewY);
        }

        // Move player and count move.
        player.moveTo(newX, newY);
        moves++;
        return true;
    }

    // Bound checking helper.
    private boolean isValidPosition(int x, int y) {
        return y >= 0 && y < map.length && x >= 0 && x < map[y].length;
    }

    // Returns the crate at a specific location (if any).
    private Crate getCrateAt(int x, int y) {
        for (Crate crate : crates) {
            Position pos = crate.getPosition();
            if (pos.getX() == x && pos.getY() == y) {
                return crate;
            }
        }
        return null;
    }

    // A level is complete when every crate sits exactly on a target tile.
    public boolean isLevelComplete() {
        for (Crate crate : crates) {
            boolean onTarget = false;

            for (Position target : targets) {
                if (crate.getPosition().equals(target)) {
                    onTarget = true;
                    break;
                }
            }

            // Any crate not on a target → level not done.
            if (!onTarget) return false;
        }
        return true;
    }

    // Encapsulated getters for external reading.
    public int[][] getMap() { return map; }
    public Player getPlayer() { return player; }
    public ArrayList<Crate> getCrates() { return crates; }
    public ArrayList<Position> getTargets() { return targets; }
    public int getMoves() { return moves; }
    public int getWidth() { return map[0].length; }
    public int getHeight() { return map.length; }
}