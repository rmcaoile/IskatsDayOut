package levels;

import static utilz.HelpMethods.*;

import java.util.ArrayList;

import entities.Doggy;
import entities.Doggy2;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import main.Game;

/**
 * Represents a game level, containing level data, enemy information, and player spawn point.
 */
public class Level {

	private Image img; 						// The image representing the level.
    private int[][] lvlData; 				// 2D array storing the tile indices for each position in the level.
    private ArrayList<Doggy> dogs; 			// List of Doggy enemies in the level.
    private ArrayList<Doggy2> dogs2; 		// List of Doggy2 enemies in the level.
    private int lvlTilesWide; 				// Width of the level in tiles.
    private int maxTilesOffset; 			// Maximum offset in tiles before reaching the end of the level.
    private int maxLvlOffsetX; 				// Maximum offset in pixels before reaching the end of the level in the x-direction.
    private Point2D playerSpawn; 			// Coordinates of the player spawn point.

    /**
     * Constructs a Level object using the provided image.
     * @param img The image representing the level.
     */
    public Level(Image img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        // Calculate the player spawn point using utility method from HelpMethods class.
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = (int) img.getWidth(); // Cast the double width to int.
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        // Create Doggy and Doggy2 enemies using utility methods from HelpMethods class.
        dogs = GetDogs(img);
        dogs2 = GetDogs2(img);
    }

    private void createLevelData() {
        // Extract the tile indices for each position in the level using utility method from HelpMethods class.
        lvlData = GetLevelData(img);
    }

    /**
     * Gets the sprite index at the specified coordinates in the level.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The sprite index at the specified coordinates.
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    
    /**
     * Gets the level data as a 2D array.
     * @return The level data.
     */
    public int[][] getLevelData() {
        return lvlData;
    }

    /**
     * Gets the maximum level offset in the x-direction.
     * @return The maximum level offset.
     */
    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    /**
     * Gets the list of Doggy enemies in the level.
     * @return The list of Doggy enemies.
     */
    public ArrayList<Doggy> getDoggy() {
        return dogs;
    }

    /**
     * Gets the list of Doggy2 enemies in the level.
     * @return The list of Doggy2 enemies.
     */
    public ArrayList<Doggy2> getDoggy2() {
        return dogs2;
    }

    /**
     * Gets the player spawn point as a Point2D object.
     * @return The player spawn point.
     */
    public Point2D getPlayerSpawn() {
        return playerSpawn;
    }
}
