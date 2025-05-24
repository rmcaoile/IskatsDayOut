package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

import javafx.scene.shape.Rectangle;
import main.Game;

public class Doggy extends Enemy {
	
	private int attackBoxOffsetX;
		
	public Doggy(double x, double y) {
		super(x, y, DOGGY_WIDTH, DOGGY_HEIGHT, DOGGY);
		initHitbox(DOGGY_HITBOX_WIDTH, DOGGY_HITBOX_HEIGHT);		// 22 width of DOGGY	// 19 height of DOGGY
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle(x, y, (int) (55 * Game.SCALE), (int) (31 * Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 10);   // 30 on left and 30 on right  // 20 on middle
	}
	
	/**
	 * Updates the Doggy's state, position, and animation based on the game conditions.
	 * @param lvlData The level data representing the game environment.
	 * @param player The player object interacting with the Doggy.
	 */
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData,  player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	//	Updates the position of the Doggy's attack box.
	private void updateAttackBox() {
	    attackBox.setX(hitbox.getX() - attackBoxOffsetX);
	    attackBox.setY(hitbox.getY());
	}

	
	/**
	 * Updates the movement and behavior of the Doggy based on the game conditions.
	 * @param lvlData The level data representing the game environment.
	 * @param player The player object interacting with the Doggy.
	 */
	private void updateMove(int[][] lvlData, Player player) {
	    // Checks if it is the Doggy's first update
	    if (firstUpdate) 
	        firstUpdateCheck(lvlData);

	    // Checks if the Doggy is in the air
	    if (inAir) {
	        // Updates the Doggy's position while in the air
	        updateInAir(lvlData);
	        
	    } else {
	        // Switches between different states of the Doggy's movement and behavior
	        switch (state) {
	            case IDLE:
	                // Sets the Doggy's state to running when idle
	                newState(RUNNING);
	                break;
	            case RUNNING:
	                // Checks if the Doggy sees the player, and sets the state to attack if close
	                if (canSeePlayer(lvlData, player)) {
	                    turnTowardsPlayer(player);
	                    if (isPlayerCloseForAttack(player))
	                        newState(ATTACK);
	                }
	                // Moves the Doggy
	                move(lvlData);
	                break;
	            case ATTACK:
	                // Checks if the Doggy's attack frame is reached and performs the attack
	                if (aniIndex == 0)
	                    attackChecked = false;

	                if (aniIndex == 2 && !attackChecked) { // Index 3 is its attack frame
	                    checkPlayerHit(attackBox, player);
	                }
	                break;
	            case HIT:
	                // Handles Doggy's behavior when hit
	                break;
	        }
	    }
	}

	
	
	public int flipX() {
		if (walkDir == LEFT)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == LEFT)
			return -1;
		else
			return 1;

	}
	
	
	
}




