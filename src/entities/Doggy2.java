package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

import javafx.scene.shape.Rectangle;
import main.Game;

public class Doggy2 extends Enemy {
	
	private int attackBoxOffsetX;
		
	public Doggy2(double x, double y) {
		super(x, y, DOGGY_WIDTH, DOGGY_HEIGHT, DOGGY2);
		initHitbox(DOGGY_HITBOX_WIDTH, DOGGY_HITBOX_HEIGHT);		// 22 width of DOGGY	// 19 height of DOGGY
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle(x, y, (int) (55 * Game.SCALE), (int) (31 * Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 10);   // 30 on left and 30 on right  // 20 on middle
	}
	
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData,  player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
	    attackBox.setX(hitbox.getX() - attackBoxOffsetX);
	    attackBox.setY(hitbox.getY());
	}

	
	private void updateMove(int[][] lvlData, Player player) {
	    if (firstUpdate) 
	    	firstUpdateCheck(lvlData);

	    if (inAir) {
	    	updateInAir(lvlData);
	    	
	    } else {
	        switch (state) {
	            case IDLE:
	            	newState(RUNNING);
	                break;
	            case RUNNING:
	            	if (canSeePlayer(lvlData, player)) {
	            		turnTowardsPlayer(player);
					if (isPlayerCloseForAttack(player))
						newState(ATTACK);
	            	}
	            
					move(lvlData);
	                break;
	            
	            case ATTACK:
					if (aniIndex == 0)
						attackChecked = false;

					if (aniIndex == 2 && !attackChecked)		// index 3 is its attack frame
						checkPlayerHit(attackBox, player);

					break;
				case HIT:
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




