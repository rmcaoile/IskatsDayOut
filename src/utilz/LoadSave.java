package utilz;

import javafx.scene.image.Image;

// Utility class for loading and saving game-related resources.
public class LoadSave {
    private static Image img;

    // File names for sprite atlases
    public static final String PLAYER_ATLAS = "Player_Cat_Sprite.png";
    public static final String LEVEL_ATLAS = "stone_tiles.png";

    // File names for enemy sprites
    public static final String DOGGY_SPRITE = "Enemy_Dog_sprite.png";
    public static final String DOGGY2_SPRITE = "Enemy_Dog2_sprite.png";

    // File names for UI elements
    public static final String HEARTS_UI = "ui_hearts";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

    
    /* Retrieves a sprite atlas image based on the given file name.
     * @param fileName The name of the sprite atlas file.
     * @return The loaded sprite atlas image.
     */
    public static Image GetSpriteAtlas(String fileName) {
        try {
            img = new Image("images/" + fileName);
        } catch (Exception e) {
            // Handle or log the exception (e.g., file not found)
            e.printStackTrace();
        }
        return img;
    }

    // Retrieves an array of level images from the "lvls" directory
    public static Image[] GetAllLevels() {
        String path = "images/lvls";
        int numLevels = 3;

        Image[] images = new Image[numLevels];

        for (int i = 0; i < numLevels; i++) {
            String imagePath = path + "/" + (i + 1) + ".png";

            images[i] = new Image(imagePath);
            System.out.println(imagePath);
        }

        return images;
    }

    // Retrieves a health display image based on the given file name and current health value
    public static Image GetHealthDisplay(String fileName, int currentHealth) {
        try {
            img = new Image("images/health/" + fileName + currentHealth + ".png");
        } catch (Exception e) {
            // Handle or log the exception (e.g., file not found)
            e.printStackTrace();
        }
        return img;
    }
}
