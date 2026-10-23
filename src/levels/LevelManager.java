package levels;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import main.Game;
import gamestates.Gamestate;
import utilz.LoadSave;

/**
 * Manages game levels, including loading, updating, and drawing them.
 */
public class LevelManager {

    private Game game; 					// The game instance.
    private Image[] levelSprite; 		// Array storing individual tile sprites for rendering levels.
    private ArrayList<Level> levels; 	// List of levels in the game.
    private int lvlIndex = 0; 			// Index indicating the current level.


    /**
     * Constructs a LevelManager for the specified game.
     * @param game The Game instance.
     */
    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    /**
     * Loads the next level in the sequence.
     * If there are no more levels, the game is considered completed, and the state transitions to the menu.
     */
    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more levels! Game Completed!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }
    
    
	
	// Builds all levels by loading images from external files and creating Level
    private void buildAllLevels() {
        Image[] allLevels = LoadSave.GetAllLevels();
        for (Image img : allLevels) {
            levels.add(new Level(img));
        }
    }

    
    // Imports the outside sprites for the level from a sprite atlas image.
    private void importOutsideSprites() {
        // Load the sprite atlas image for the level.
        Image img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        
        // Initialize the array to store level sprites.
        levelSprite = new Image[48];
        int spriteWidth = 32;
        int spriteHeight = 32;

        // Loop through rows (j) and columns (i) to extract sub-images from the sprite atlas.
        PixelReader reader = img.getPixelReader();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                // Calculate the index for the current sub-image.
                int index = j * 12 + i;
                
                // Create a WritableImage to represent the sub-image.
                WritableImage tile = new WritableImage(spriteWidth, spriteHeight);
                // Copy the sub-image pixels from the sprite atlas, preserving transparency.
                tile.getPixelWriter().setPixels(0, 0, spriteWidth, spriteHeight, reader, i * spriteWidth, j * spriteHeight);

                // Store the sub-image in the levelSprite array.
                levelSprite[index] = tile;
            }
        }
    }

    /**
     * Draws the current level on the provided GraphicsContext.
     * @param gc The GraphicsContext to draw on.
     * @param lvlOffset The level offset in the x-direction.
     */
    public void draw(GraphicsContext gc, int lvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                if (index == 11) {
                    continue;
                }
                gc.drawImage(
                        levelSprite[index],
                        Game.TILES_SIZE * i - lvlOffset,
                        Game.TILES_SIZE * j,
                        Game.TILES_SIZE,
                        Game.TILES_SIZE
                );
            }
        }
    }

    /**
     * Updates the current level (currently empty).
     */
    public void update() {
        // Currently empty
    }

    
    /**
     * Gets the current level.
     * @return The current level.
     */
    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    
    /**
     * Gets the total number of levels.
     * @return The total number of levels.
     */
    public int getAmountOfLevels() {
        return levels.size();
    }

    
    /**
     * Gets the index of the current level.
     * @return The index of the current level.
     */
    public int getLvlIndex() {
        return lvlIndex;
    }
}
