package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import gamestates.Playing;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import main.Game;
import utilz.LoadSave;

// The Player class represents the player character in the game.
public class Player extends Entity {

	private Image[][] animations; 							// A 2D array storing different animation frames for each player action.
    private Image img; 										// The sprite sheet image containing various player animations.

    private boolean moving = false, attacking = false; 		// Flags indicating whether the player is moving or attacking.
    private boolean left, right, jump, running; 			// Flags representing the player's movement direction.
    private boolean sleeping, licking, eating, scratching, pooping, looking, tricks, spin, chills, surprised, revive;		// Flags representing various player actions.
    private int[][] lvlData;
	
    // Constants for player dimensions and hitbox.
    private float xDrawOffset = 2*12 * Game.SCALE;		// Hitbox starts with x=16 and y=16	
	private float yDrawOffset = 2*16 * Game.SCALE;
	public static int PLAYER_HITBOX_WIDTH = 12;			// Width of the hitbox of Player
	public static int PLAYER_HITBOX_HEIGHT = 15;		// Height of the hitbox of Player
	public static int SPRITE_WIDTH = 32;				// Width of each frame in the sprite sheet
	public static int SPRITE_HEIGHT = 32;				// Height of each frame in the sprite sheet
	
	 // Jumping / Gravity parameters.
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	
	// HealthUI
	private Image healthDisplay;
	private int healthDisplayWidth = (int) (99 * Game.SCALE * 2);		// 99 width
	private int healthDisplayHeight = (int) (11 * Game.SCALE * 2);		// 11 height
	private int healthDisplayX = (int) (15 * Game.SCALE);
	private int healthDisplayY = (int) (20 * Game.SCALE);

	// Attack damage of cat
	public static int attackDamage = 1;
	// Walk speed of cat
	public static float walkSpeed = 1.0f * Game.SCALE;
	
	// Flip X and W for rendering.
	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;
	
	// Constructor
	public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 9;					// Health of cat
		this.currentHealth = maxHealth;
        loadAnimations();
        initHitbox(2*PLAYER_HITBOX_WIDTH, 2*PLAYER_HITBOX_HEIGHT);		
		initAttackBox();
    }
	
	// Set the spawn point of the player.
	public void setSpawn(Point2D spawn) {
		this.x = spawn.getX();
		this.y = spawn.getY();
		hitbox.setX(x);
		hitbox.setY(y);
	}
	
	// Attack box of cat
	private void initAttackBox() {
		attackBox = new Rectangle(x, y, (int) (30 * Game.SCALE), (int) (20 * Game.SCALE));		
	}
	
	// Updates the player's position, animation, and other properties.
	public void update() {
		updateAttackBox();
		
		// Update that dictates when is the game over; which is when Cat health is at 0
		if (currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		// Updates the player's position
		updatePos(); 				
		if (attacking)
			checkAttack();
		
		updateAnimationTick(); 		// Updates the animation frame based on timing.
        setAnimation(); 			// Sets the appropriate animation based on player state.
    }

	/**
	 * Checks for an attack and performs actions accordingly.
	 * If the attack has already been checked or the animation index is not 2, no further action is taken.
	 * When a valid attack is detected, sets the 'attackChecked' flag to true and checks for enemy hits using the attack box.
	 */
	private void checkAttack() {
		if (attackChecked || aniIndex != 2)		// 2  is the index of atack of player
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);

	}

	//	Updates the position of the attack box based on the player's direction.
	private void updateAttackBox() {
	    double attackBoxOffset = Game.SCALE * 9;

	    if (right) {
	    	// If the player is facing right, position the attack box to the right of the player's hitbox
	        attackBox.setX(hitbox.getX() + hitbox.getWidth() - attackBoxOffset);
	    } else if (left) {
	    	// If the player is facing left, position the attack box to the left of the player's hitbox
	        attackBox.setX(hitbox.getX() - hitbox.getWidth() + attackBoxOffset);
	    }
	    // Set the Y coordinate of the attack box above the player's hitbox by the specified offset
	    attackBox.setY(hitbox.getY() + attackBoxOffset);
	}
	
	
	// Renders the player 
    // @param gc The GraphicsContext on which the player is rendered.
	public void render(GraphicsContext gc, int lvlOffset) {
	    // Draw the current frame of the player's animation on the canvas
		gc.drawImage(
	            animations[state][aniIndex],  						// The image to be drawn (current frame of player's animation)
	            0, 0, SPRITE_WIDTH, SPRITE_HEIGHT,					// Source rectangle (frame) in the sprite sheet
	            hitbox.getX() - xDrawOffset - lvlOffset + flipX,	// Destination rectangle on the canvas 	// Flip image based on direction
	            hitbox.getY() - yDrawOffset, 
	            2*width * flipW, 2*height 	 						// Size of Cat Player
	    );
//		drawHitbox(gc, lvlOffset);
//	    drawAttackBox(gc, lvlOffset);
		drawUI(gc);
	}

	
	private void drawUI(GraphicsContext gc) {
		// Retrieve the health display image from the saved resources
    	healthDisplay = LoadSave.GetHealthDisplay(LoadSave.HEARTS_UI, currentHealth);
    	// Draw the health display image on the GraphicsContext at the specified position and size
        gc.drawImage(healthDisplay, healthDisplayX, healthDisplayY, healthDisplayWidth, healthDisplayHeight);
    }
	
	
	/* Increments the animation tick, controlling the timing of animation frames.
	 * Resets the animation tick and updates the animation index when the specified
	 * animation speed is reached. Also resets the some flag when the animation completes.
	 */
	private void updateAnimationTick() {
	    // Increments the animation tick
	    aniTick++;

	    // Checks if the animation tick has reached the specified animation speed
	    if (aniTick >= ANI_SPEED) {
	        aniTick = 0; // Resets the animation tick

	        // Increments the animation index
	        aniIndex++;

	        // Checks if the animation index has reached the total number of frames for the current action
	        if (aniIndex >= GetSpriteAmount(state)) {
	            aniIndex = 0; 			// Resets the animation index
	            attacking = false; 		// Resets the attacking flag when the attack animation completes
	            attackChecked = false;
	            sleeping = false; 
	            licking = false; 
	            eating = false;
	            scratching = false;
	            pooping = false;
	            looking = false;
	            tricks = false;
	            spin = false;
	            chills = false;
	            surprised = false;
	            revive = false;
	        }
	    }
	}

    
	/**
	 * Sets the appropriate animation state for the player based on various conditions such as movement,
	 * actions (e.g., attacking, sleeping), and the player's current state (e.g., in the air, on the ground).
	 * This method updates the 'state' variable to determine which animation frames to use for rendering.
	 * It also handles special cases, such as resetting the animation tick and index when transitioning
	 * to a different animation state.
	 */
	private void setAnimation() {
	    int startAni = state;

	 
	    if (attacking) {
	    	// Set the animation state to ATTACK2 when the player is attacking
	    	state = ATTACK2;
	    	
	    	// Reset animation tick and index when transitioning to ATTACK2
	        if (startAni != ATTACK2) {
	            aniIndex = 2;
	            aniTick = 0;
	            return;
	        }
	        
	    } else if (inAir) {
	        if (airSpeed < 0)
	        	state = JUMP;
	        else
	        	state = FALLING;
	        
	    } else if (moving && running) {
	    	state = RUNNING3;
	    } else if (moving) {
	    	state = WALKING;
	        
	    } else if (sleeping) {
	    	state = SLEEP;
	    } else if (licking) {
	    	state = LICK;
	    } else if (eating) {
	    	state = EAT;
	    } else if (scratching) {
	    	state = SCRATCH;
	    } else if (pooping) {
	    	state = POOP;
	    } else if (looking) {
	    	state = LOOK_AROUND;
	    } else if (tricks) {
	    	state = TRICKS;
	    } else if (spin) {
	    	state = SPIN;
	    } else if (chills) {
	    	state = CHILLS;
	    } else if (surprised) {
	    	state = SURPRISED;
	    } else if (revive) {
	    	state = REVIVE;
	        
	    } else {
	    	state = IDLE;
	    }

	    // Reset animation tick and index when transitioning to a different animation state
	    if (startAni != state)
	        resetAnitick();
	}

    
    private void resetAnitick() {
    	// Resets the animation tick and index when the animation changes.
        aniTick = 0;
    	aniIndex = 0;
	}

    
    /**
     * Updates the player's position based on movement flags, taking into account actions such as jumping,
     * falling, and moving left or right. This method calculates the horizontal speed based on the movement
     * flags, adjusts the player's position, and handles collision detection with the environment. It also
     * manages the player's state, such as being in the air or on the ground, and triggers specific actions
     * accordingly, such as jumping or resetting in-air status after a collision.
     */
    private void updatePos() {
        // Indicates whether the player is currently moving
        moving = false;

        // Perform a jump if the 'jump' flag is set
        if (jump)
            jump();

        // If the player is not in the air and is not moving left or right or is moving both left and right, return
        if (!inAir && ((!left && !right) || (left && right)))
            return;

        float xSpeed = 0;

        // Adjust horizontal speed based on movement flags
        if (left) {
            xSpeed -= walkSpeed;
            flipX = width * 2;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        // If the player is not in the air and is not on the floor, set the in-air flag to true
        if (!inAir && !IsEntityOnFloor(hitbox, lvlData))
            inAir = true;

        // If the player is in the air, update the position based on air speed and gravity
        if (inAir) {
            if (CanMoveHere(hitbox.getX(), (hitbox.getY() + airSpeed), hitbox.getWidth(), hitbox.getHeight(), lvlData)) {
                hitbox.setY(hitbox.getY() + airSpeed);
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                // If a collision occurs, adjust the position and handle in-air status
                hitbox.setY(GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed));
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            // If the player is on the ground, update the position based on horizontal speed
            updateXPos(xSpeed);

        // Set the moving flag to true
        moving = true;
    }

	

	
	private void jump() {
		// If the player is already in the air, do nothing
		if (inAir)
			return;
		// Set the in-air flag to true
	    inAir = true;
	    // Set the air speed to the predefined jump speed, initiating the jump action
	    airSpeed = jumpSpeed;
	}

	private void resetInAir() {
	    // Set the in-air flag to false, indicating that the player is no longer airborne
	    inAir = false;

	    // Reset the air speed to zero
	    airSpeed = 0;
	}
	
	/* Updates the player's horizontal position based on the given horizontal speed.
	 * Attempts to move the player horizontally, adjusting the position in case of a collision.
	 * @param xSpeed The horizontal speed at which the player is moving.
	 */
	private void updateXPos(float xSpeed) {
	    // Attempt to move horizontally
	    if (CanMoveHere((hitbox.getX() + xSpeed), (hitbox.getY()), hitbox.getWidth(), hitbox.getHeight(), lvlData)) {
	        // Move if there's no collision
	        hitbox.setX(hitbox.getX() + xSpeed);
	    } else {
	        // If there's a collision, reposition the entity next to the wall
	        hitbox.setX(GetEntityXPosNextToWall(hitbox, xSpeed));
	    }
	}

	
	public void changeHealth(int value) {
		currentHealth += value;

		// Ensure current health remains within the valid range [0, maxHealth]
	    if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	
	/* Loads player animations from the sprite sheet and populates the animations array.
	 * Uses the player atlas sprite sheet to create individual animation frames.
	 */
	private void loadAnimations() {
		// Retrieves the player atlas sprite sheet
		img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		
		// Initializes the animations array with dimensions [51][32]
		animations = new Image[51][32];
		
		// Obtains the pixel reader from the sprite sheet
		PixelReader pixelReader = img.getPixelReader();
		
		// Iterates through the animations array to populate it with individual frames
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				// Calculates the offset for the current frame
				int offsetX = i * SPRITE_WIDTH;
				int offsetY = j * SPRITE_HEIGHT;
				
				// Creates a writable image for the current frame using the pixel reader
				WritableImage sprite = new WritableImage(pixelReader, offsetX, offsetY, SPRITE_WIDTH, SPRITE_HEIGHT);
				
				// Populates the animations array with the current frame
				animations[j][i] = sprite;
			}
		}
	}
	
	
	/**
	 * Loads the level data and initializes the player's level data.
	 * Sets the player as in the air if not on the floor based on the initial level data.
	 *
	 * @param lvlData The 2D array representing the level data.
	 */
	public void loadLvlData(int[][] lvlData) {
	    this.lvlData = lvlData;
	    
	    // Check if the player is not on the floor and set inAir flag accordingly
	    if (!IsEntityOnFloor(hitbox, lvlData)) {
	        inAir = true;
	    }
	}

	
	public void resetDirBooleans() {
		// Resets the movement direction flags.
	    left = false;
		right = false;
	}
	
	/**
	 * Resets various properties and flags of the player to their default states.
	 * - Resets movement direction flags.
	 * - Sets the player's status to on-ground.
	 * - Resets attacking, moving, and state flags.
	 * - Resets the player's health to the maximum value.
	 * - Resets the player's hitbox position to the initial spawn point.
	 * - Sets the player to an on-ground state if not already on the floor.
	 */
	public void resetAll() {
	    resetDirBooleans();
	    inAir = false;
	    attacking = false;
	    moving = false;
	    state = IDLE_SIT_TAIL_DOWN;
	    currentHealth = maxHealth;

	    hitbox.setX(x);
	    hitbox.setY(y);

	    if (!IsEntityOnFloor(hitbox, lvlData))
	        inAir = true;
	}

	
	public static int getAttackDamage() {
		return attackDamage;
	}

	public static void setAttackDamage(int attackDamage) {
		Player.attackDamage = attackDamage;
	}
	
	public static void setWalkSpeed(float accelerate) {
		Player.walkSpeed = walkSpeed*accelerate;
	}
	public void setRun(boolean running) {
		this.running = running;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public void setLicking(boolean licking) {
		this.licking = licking;
	}

	public void setEating(boolean eating) {
		this.eating = eating;
	}

	public void setScratching(boolean scratching) {
		this.scratching = scratching;
	}

	public void setPooping(boolean pooping) {
		this.pooping = pooping;
	}

	public void setLooking(boolean looking) {
		this.looking = looking;
	}

	public void setTricks(boolean tricks) {
		this.tricks = tricks;
	}

	public void setSpin(boolean spin) {
		this.spin = spin;
	}

	public void setChills(boolean chills) {
		this.chills = chills;
	}

	public void setSurprised(boolean surprised) {
		this.surprised = surprised;
	}

	public void setRevive(boolean revive) {
		this.revive = revive;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getState() {
		return state;
	}

	public void setPlayerAction(int playerAction) {
		this.state = playerAction;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	
}




