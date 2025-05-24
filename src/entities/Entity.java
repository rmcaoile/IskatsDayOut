package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Game;


/**
 * The abstract base class representing entities in the game.
 * Entities have a position (x, y), dimensions (width, height),
 * and a hitbox for collision detection. It also includes methods
 * for drawing the hitbox and attack box for debugging purposes.
 */
public abstract class Entity {

    protected double x, y;         		// The x and y-coordinate of the entity's position
    protected int width, height;    	// The width and height of the entity's bounding box
    protected Rectangle hitbox;     	// The rectangular hitbox used for collision detection
    protected int aniTick, aniIndex; 	// Animation-related variables (tick and index)
    protected int state;             	// Current state of the entity (e.g., idle, running, attacking)
    protected float airSpeed;       	// Speed of the entity while in the air (jumping/falling)
    protected boolean inAir = false; 	// Flag indicating whether the entity is in the air
    protected int maxHealth;         	// Maximum health points of the entity
    protected int currentHealth;     	// Current health points of the entity
    protected Rectangle attackBox;   	// The rectangular area representing the entity's attack range
    protected float walkSpeed;       	// Speed of the entity while walking on the ground

    /**
     * Constructs an entity with the specified position and dimensions.
     * @param x      The x-coordinate of the entity's position.
     * @param y      The y-coordinate of the entity's position.
     * @param width  The width of the entity.
     * @param height The height of the entity.
     */
    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Draws the hitbox of the entity for debugging purposes.
    protected void drawHitbox(GraphicsContext gc, int xLvlOffset) {
        gc.setStroke(hitbox.getStroke());
        gc.strokeRect(
                hitbox.getX() - xLvlOffset,
                hitbox.getY(),
                hitbox.getWidth(),
                hitbox.getHeight()
        );
    }

  
    // Draws the attack box of the entity for debugging purposes.
    protected void drawAttackBox(GraphicsContext gc, int lvlOffsetX) {
        gc.setStroke(Color.RED);
        gc.strokeRect(attackBox.getX() - lvlOffsetX, attackBox.getY(), attackBox.getWidth(), attackBox.getHeight());
    }

    
    // Initializes the hitbox with the specified width and height.
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }
    

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getState() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}


