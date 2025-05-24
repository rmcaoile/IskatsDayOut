package entities;

import static utilz.Constants.EnemyConstants.*;

import java.util.ArrayList;
import gamestates.Playing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import levels.Level;
import utilz.LoadSave;

public class EnemyManager {

	private Playing playing;
	private Image[][] doggyArr;
	private Image[][] doggy2Arr;
	private ArrayList<Doggy> doggies = new ArrayList<>();
	private ArrayList<Doggy2> doggies2 = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
    /**
     * Loads the enemies from the specified level.
     * @param level The Level instance containing enemy data.
     */
    public void loadEnemies(Level level) {
        doggies = level.getDoggy();
        doggies2 = level.getDoggy2();
    }
	
    // Updates the enemies based on the current game state and player position.
	// This is where level complete is stated
    public void update(int[][] lvlData, Player player) {
        // Flag to check if any enemy is active
        boolean isAnyActive = false;

        // Update Doggy entities
        for (Doggy d : doggies)
            if (d.isActive()) {
                d.update(lvlData, player);
                isAnyActive = true;
            }

        // Update Doggy2 entities
        for (Doggy2 d2 : doggies2)
            if (d2.isActive()) {
                d2.update(lvlData, player);
                isAnyActive = true;
            }

        // If no enemy is active, set the level as completed
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    /**
     * Draws the enemies on the screen.
     * @param xLvlOffset The x-coordinate offset for the level.
     */
	public void draw(GraphicsContext gc, int xLvlOffset) {
	    drawDogs(gc, xLvlOffset);
	    drawDogs2(gc, xLvlOffset);
	}
	
	private void drawDogs(GraphicsContext gc, int xLvlOffset) {
	    for (Doggy d : doggies) {
	        Image dogImage = doggyArr[d.getState()][d.getAniIndex()];
	        if (d.isActive()) {
		        gc.drawImage(
		        		dogImage, 
		        		d.getHitbox().getX() - DOGGY_DRAWOFFSET_X - xLvlOffset + d.flipX(), 
		        		d.getHitbox().getY() - DOGGY_DRAWOFFSET_Y, 
		        		DOGGY_WIDTH * d.flipW(), DOGGY_HEIGHT);
//		        d.drawHitbox(gc, xLvlOffset);
//				d.drawAttackBox(gc, xLvlOffset);
	        }
	    }
	    
	    
	}
	
	private void drawDogs2(GraphicsContext gc, int xLvlOffset) {
		for (Doggy2 d2 : doggies2) {
	        Image dogImage = doggy2Arr[d2.getState()][d2.getAniIndex()];
	        if (d2.isActive()) {
		        gc.drawImage(
		        		dogImage, 
		        		d2.getHitbox().getX() - DOGGY_DRAWOFFSET_X - xLvlOffset + d2.flipX(), 
		        		d2.getHitbox().getY() - DOGGY_DRAWOFFSET_Y, 
		        		DOGGY_WIDTH * d2.flipW(), DOGGY_HEIGHT);
//		        d2.drawHitbox(gc, xLvlOffset);
//				d2.drawAttackBox(gc, xLvlOffset);
	        }
	    }
	}
	
	
	/**
	 * Checks if the player's attack hits any active enemy entities and applies damage.
	 * @param attackBox The rectangle representing the player's attack area.
	 */
	public void checkEnemyHit(Rectangle attackBox) {
	    // Check for hits on Doggy entities
	    for (Doggy d : doggies)
	        if (d.isActive())
	            if (attackBox.intersects(d.getHitbox().getBoundsInLocal())) {
	                d.hurt(Player.getAttackDamage());
	                return;
	            }

	    // Check for hits on Doggy2 entities
	    for (Doggy2 d2 : doggies2)
	        if (d2.isActive())
	            if (attackBox.intersects(d2.getHitbox().getBoundsInLocal())) {
	                d2.hurt(Player.getAttackDamage());
	                return;
	            }
	}

	
	
	private void loadEnemyImgs() {
		// Load images for Doggy enemies
		doggyArr = new Image[DOGGY_NUM_STATES][DOGGY_MAX_INDEX];		// 5 states 	// 9 max index
		Image img = LoadSave.GetSpriteAtlas(LoadSave.DOGGY_SPRITE);
		
		PixelReader pixelReader = img.getPixelReader();
		
		for (int j = 0; j < doggyArr.length; j++)
			for (int i = 0; i < doggyArr[j].length; i++) {
				int offsetX = i * DOGGY_WIDTH_DEFAULT;
		        int offsetY = j * DOGGY_HEIGHT_DEFAULT;
		
		        WritableImage sprite = new WritableImage(pixelReader, offsetX, offsetY, DOGGY_WIDTH_DEFAULT, DOGGY_HEIGHT_DEFAULT);
		        doggyArr[j][i] = sprite;
			}
		
		// Load images for Doggy2 enemies
		doggy2Arr = new Image[DOGGY_NUM_STATES][DOGGY_MAX_INDEX];		// 5 states 	// 9 max index
		Image img2 = LoadSave.GetSpriteAtlas(LoadSave.DOGGY2_SPRITE);
		
		PixelReader pixelReader2 = img2.getPixelReader();
		
		for (int j = 0; j < doggy2Arr.length; j++)
			for (int i = 0; i < doggy2Arr[j].length; i++) {
				int offsetX = i * DOGGY_WIDTH_DEFAULT;
		        int offsetY = j * DOGGY_HEIGHT_DEFAULT;
		
		        WritableImage sprite = new WritableImage(pixelReader2, offsetX, offsetY, DOGGY_WIDTH_DEFAULT, DOGGY_HEIGHT_DEFAULT);
		        doggy2Arr[j][i] = sprite;
			}
	}
	
    /**
     * Resets all enemies to their initial state.
     */
    public void resetAllEnemies() {
        for (Doggy d : doggies)
            d.resetEnemy();

        for (Doggy2 d2 : doggies2)
            d2.resetEnemy();
    }
    
    
}



