package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.*;
import static utilz.HelpMethods.*;

import javafx.scene.shape.Rectangle;
import main.Game;
import utilz.HelpMethods;

public abstract class Enemy extends Entity {

    // Additional fields for enemy-specific behavior
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int hurtTimer = 0;
    protected int hurtDir = 1;
    protected boolean hurt = false;
	
	
	public Enemy(double x, double y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.35f;
	}
	
	
    /**
     * Checks if this is the first update and if the enemy is in the air.
     * If in the air, sets the inAir flag to true.
     * @param lvlData The level data representing the game world.
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }
	
	
    /**
     * Moves the enemy horizontally based on its walking direction.
     * Changes walking direction if there's an obstacle.
     * @param lvlData The level data representing the game world.
     */
    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        // Determine the horizontal speed based on the walking direction
        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        // Check if the enemy can move to the new position without collision
        if (CanMoveHere(hitbox.getX() + xSpeed, hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), lvlData))
        	// Check if the path is clear for movement (no floor obstacle)
            if (IsFloor(hitbox, xSpeed, lvlData)) {
            	// Move the enemy horizontally
                hitbox.setX(hitbox.getX() + xSpeed);
                return;		// Exit the method, movement successful
            }
        // If there is an obstacle, change the walking direction
        changeWalkDir();
    }
	
    
    protected void turnTowardsPlayer(Player player) {
        // Check if the player is to the right or left of the enemy
        if (player.getHitbox().getX() > hitbox.getX()) {
            // If the player is to the right, set the walking direction to RIGHT
            walkDir = RIGHT;
        } else {
            // If the player is to the left, set the walking direction to LEFT
            walkDir = LEFT;
        }
    }
	
	
    /**
     * Checks if the enemy has a clear line of sight to the player, considering the game level layout.
     * @param lvlData The 2D array representing the game level layout.
     * @param player The player object to be checked for visibility.
     * @return True if the enemy can see the player, false otherwise.
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        // Get the tile Y-coordinate of the player's hitbox
        int playerTileY = (int) (player.getHitbox().getY() / Game.TILES_SIZE);

        // Check if the player is on the same tile Y-coordinate as the enemy
        if (playerTileY == tileY)
            // Check if the player is within the attack range and there is a clear line of sight
            if (isPlayerInRange(player)) {
                if (HelpMethods.IsSightClear(lvlData, hitbox, player.getHitbox(), tileY))
                    return true;
            }

        // Return false if the player is not visible to the enemy
        return false;
    }

	
    protected boolean isPlayerInRange(Player player) {
        // Calculate the absolute horizontal distance between the enemy and the player
        int absValue = (int) Math.abs(player.hitbox.getX() - hitbox.getX());

        // Check if the player is within 5 times the attack distance (line of sight range)
        return absValue <= attackDistance * 5;
    }
	
    protected boolean isPlayerCloseForAttack(Player player) {
        // Calculate the absolute horizontal distance between the enemy and the player
        int absValue = (int) Math.abs(player.hitbox.getX() - hitbox.getX());

        // Check if the player is within the attack distance
        return absValue <= attackDistance;
    }
	
	protected void newState(int enemyState) {
		this.state = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	/**
	 * Inflicts damage to the enemy and updates its state accordingly.
	 * @param amount The amount of damage to be inflicted.
	 */
	public void hurt(int amount) {
		hurt(amount, 1);
	}

	public void hurt(int amount, int knockbackDir) {
		// Ignore hits while a hit/death animation is already playing, so the
		// animation cannot be reset by repeatedly hitting the enemy.
		if (state == HIT || state == DEAD)
			return;

	    // Decrease the enemy's health by the specified amount
	    currentHealth -= amount;
		hurtDir = knockbackDir;
		hurtTimer = 20;
		hurt = true;
		newState(HIT);
		// small upward knockback
		if (!inAir) {
			inAir = true;
			airSpeed = -0.75f * Game.SCALE;
		}
	}

	public boolean isInvulnerable() {
		return hurtTimer > 0;
	}

	protected void updateHurt(int[][] lvlData) {
		if (hurtTimer <= 0)
			return;
		// knockback
		float kb = hurtDir * (walkSpeed * 4.0f);
		if (CanMoveHere(hitbox.getX() + kb, hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), lvlData))
			hitbox.setX(hitbox.getX() + kb);
		// gravity
		if (inAir) {
			if (CanMoveHere(hitbox.getX(), (hitbox.getY() + airSpeed), hitbox.getWidth(), hitbox.getHeight(), lvlData)) {
				hitbox.setY(hitbox.getY() + airSpeed);
				airSpeed += GRAVITY;
			} else {
				hitbox.setY(GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed));
				if (airSpeed > 0)
					inAir = false;
				else
					airSpeed = 0;
			}
		} else if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
		hurtTimer--;
	}


	protected void checkPlayerHit(Rectangle attackBox, Player player) {
	    // Check if the enemy's attackBox intersects with the player's hitbox
	    if (attackBox.intersects(player.getHitbox().getBoundsInLocal())) {
	        // If a hit occurs, reduce the player's health by the enemy's damage
            if (!player.isInvulnerable()) {
                player.changeHealth(-GetEnemyDmg(enemyType));
                int knockbackDir = (player.getHitbox().getX() > hitbox.getX()) ? 1 : -1;
                player.startHurt(knockbackDir);
            }
	    }
	    // Mark the attack as checked
	    attackChecked = true;
	}
	
	
	protected void updateInAir(int[][] lvlData) {
	    // Check if the enemy can move vertically
	    if (CanMoveHere(hitbox.getX(), hitbox.getY() + airSpeed, hitbox.getWidth(), hitbox.getHeight(), lvlData)) {
	        // Move vertically and apply gravity
	        hitbox.setY(hitbox.getY() + airSpeed);
	        airSpeed += GRAVITY;
	    } else {
	        // If a collision occurs, update the enemy's position and tileY
	        inAir = false;
	        hitbox.setY(GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed));
	        tileY = (int) (hitbox.getY() / Game.TILES_SIZE);
	    }
	}
	
	
	protected void updateAnimationTick() {
	    aniTick++;
	    
	    // Check if it's time to advance the animation frame
	    if (aniTick >= ANI_SPEED) {
	        aniTick = 0;
	        aniIndex++;
	        
	        // Check if the animation index exceeds the maximum for the current state
	        if (aniIndex >= GetSpriteAmount(enemyType, state)) {
	            aniIndex = 0;

	            // Handle state transitions based on animation completion
	            switch (state) {
	                case ATTACK -> state = IDLE;
					case HIT -> {
						if (currentHealth <= 0) {
							newState(DEAD);
						} else {
							state = IDLE;
						}
					}
	                case DEAD -> active = false;
	            }
	        }
	    }
	}
	
	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	public void resetEnemy() {
		hitbox.setX(x);
		hitbox.setY(y);
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		state = 0;
		hurtTimer = 0;
		hurt = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	
}
