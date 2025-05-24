package gamestates;

import entities.EnemyManager;
import entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.GameWonOverlay;
import ui.LevelCompletedOverlay;

// The "Playing" state represents the main gameplay state where the player can control characters and interact with the game world.
public class Playing extends State implements Statemethods {
	
	// Player and game-related managers
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;

	// Game overlays
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private GameWonOverlay gameWonOverlay;

	// Level offset and borders
	private int xLvlOffset;
	private int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;

	// Game state flags
	private boolean gameOver;
	private boolean lvlCompleted;

		
	 /**
     * Constructs the "Playing" state.
     * @param game The game instance.
     */
	public Playing(Game game) {
		super(game);
		initClasses();
		//load bg here
		calcLvlOffset();
		loadStartLevel();
	}

	/**
	 * Loads the initial enemies and sets the player spawn position for the starting level.
	 */
	private void loadStartLevel() {
	    enemyManager.loadEnemies(levelManager.getCurrentLevel());
	    player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}

	
	/**
	 * Loads the next level, resets various game elements, and sets the player spawn position.
	 * Resets the level completion flag to prepare for the new level.
	 */
	public void loadNextLevel() {
	    // Reset game elements
	    resetAll();
	    
	    // Load the next level
	    levelManager.loadNextLevel();
	    
	    // Set the player spawn position for the new level
	    player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	    
	    // Reset the level completion flag
	    setLevelCompleted(false);
	}

	
	/**
	 * Calculates the maximum level offset based on the current level's offset value.
	 * This is used to determine the scrolling boundaries for the player's movement.
	 */
	private void calcLvlOffset() {
	    maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

	
	/**
	 * Initializes the classes required for the game, including the level manager,
	 * enemy manager, player, and various overlays.
	 */
	private void initClasses() {
	    // Initialize the level manager with the game instance
	    levelManager = new LevelManager(game);

	    // Initialize the enemy manager with the current state instance
	    enemyManager = new EnemyManager(this);

	    // Initialize the player with specific parameters and load level data
	    player = new Player(
	            200, 200,
	            (Player.SPRITE_WIDTH * (int) Game.SCALE),
	            (Player.SPRITE_HEIGHT * (int) Game.SCALE),
	            this);
	    player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

	    // Initialize overlays for game over, level completed, and game won scenarios
	    gameOverOverlay = new GameOverOverlay(this);
	    levelCompletedOverlay = new LevelCompletedOverlay(this);
	    gameWonOverlay = new GameWonOverlay(this);
	}

	
	/**
	 * Updates the game state. If the level is completed, updates the level completed overlay.
	 * Otherwise, updates the level manager, player, enemy manager, and checks player's proximity
	 * to the level borders.
	 */
	@Override
	public void update() {
	    // Check if the level is completed and update the overlay
	    if (lvlCompleted) {
	        levelCompletedOverlay.update();
	    } else if (!gameOver) {
	        // If the game is not over, update the level manager, player, and enemy manager
	        levelManager.update();
	        player.update();
	        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
	        checkCloseToBorder();
	    }
	}

	
	/**
	 * Checks if the player is close to the level borders and adjusts the level offset accordingly.
	 * This method ensures that the player remains within the specified left and right borders.
	 */
	private void checkCloseToBorder() {
	    int playerX = (int) player.getHitbox().getX();
	    int diff = playerX - xLvlOffset;

	    // Adjust the level offset based on the player's position relative to the borders
	    if (diff > rightBorder)
	        xLvlOffset += diff - rightBorder;
	    else if (diff < leftBorder)
	        xLvlOffset += diff - leftBorder;

	    // Ensure the level offset does not exceed the maximum or go below zero
	    if (xLvlOffset > maxLvlOffsetX)
	        xLvlOffset = maxLvlOffsetX;
	    else if (xLvlOffset < 0)
	        xLvlOffset = 0;
	}


	// game over and level completed shown here
	@Override
	public void draw(GraphicsContext gc) {
	    levelManager.draw(gc, xLvlOffset);
	    player.render(gc, xLvlOffset);
	    enemyManager.draw(gc, xLvlOffset);

	    // Check if the game is over or the level is completed
	    if (gameOver) {
	        gameOverOverlay.draw(gc);
	    } else if (lvlCompleted) {
	        // Use an instance of LevelManager to get the current level index
	        int currentLevelIndex = levelManager.getLvlIndex();
	        
	        // Draw the appropriate overlay based on the current level index
	        if (currentLevelIndex == 2) {
	            gameWonOverlay.draw(gc);
	        } else {
	            levelCompletedOverlay.draw(gc);
	        }
	    }
	}


	/**
	 * Resets the game state by clearing game over status, resetting the player, resetting all enemies,
	 * and marking the level as not completed.
	 */
	public void resetAll() {
	    gameOver = false;
	    player.resetAll();
	    enemyManager.resetAllEnemies();
	    setLevelCompleted(false);
	}

	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	// Checks for hits on enemies using the provided attack box. 
	public void checkEnemyHit(Rectangle attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (!gameOver)
			if (event.getButton() == javafx.scene.input.MouseButton.PRIMARY) 
			player.setAttacking(true);
	}


	@Override
	public void mousePressed(MouseEvent event) {
		if (!gameOver) {
			if (lvlCompleted)
				levelCompletedOverlay.mousePressed(event);
		}	
	}

	public void mouseDragged(MouseEvent event) {
//		if (!gameOver) {
//			if (lvlCompleted)
//				levelCompletedOverlay.mouseDragged(event);
//		}					
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!gameOver) {
			if (lvlCompleted)
				levelCompletedOverlay.mouseReleased(event);
		}					
	}


	@Override
	public void mouseMoved(MouseEvent event) {
		if (!gameOver) {
			if (lvlCompleted) {
                levelCompletedOverlay.mouseMoved(event); // Ensure levelCompletedOverlay is not null
            }
		}
	}


	@Override
	public void keyPressed(KeyEvent event) {
	    if (gameOver) {
	        gameOverOverlay.keyPressed(event);
	    } else {
            switch (event.getCode()) {
                case A, LEFT -> player.setLeft(true);
                case D, RIGHT -> player.setRight(true);
                case W, UP, SPACE -> player.setJump(true);
                case SHIFT -> {
                    Player.setWalkSpeed(2);
                    player.setRun(true);
                }
                case X -> {
                    player.setAttacking(true);
                    System.out.println("CAT PUNCH!!");
                }
                case C -> {
                    player.setAttacking(true);
                    System.out.println("Scratchy scratch!!");
                }
                case DIGIT1, S -> player.setSleeping(true);
                case DIGIT2 -> player.setLicking(true);
                case DIGIT3 -> player.setEating(true);
                case DIGIT4 -> player.setScratching(true);
                case DIGIT5, Q -> player.setPooping(true);
                case DIGIT6 -> player.setLooking(true);
                case DIGIT7, E -> player.setTricks(true);
                case DIGIT8 -> player.setSpin(true);
                case DIGIT9 -> player.setChills(true);
                case DIGIT0 -> player.setSurprised(true);
                case EQUALS -> {
                	if (player.getCurrentHealth() != 9) {
	                	player.setRevive(true);
	                	player.setCurrentHealth( player.getCurrentHealth() + 1); 
	                	}
                }
                case B, ESCAPE, BACK_SPACE -> Gamestate.state = Gamestate.MENU;
            }
	    }
	}


	@Override
	public void keyReleased(KeyEvent event) {
		if (!gameOver)
            switch (event.getCode()) {
                case A, LEFT -> player.setLeft(false);
                case D, RIGHT -> player.setRight(false);
                case W, UP, SPACE -> player.setJump(false);
                case SHIFT -> {
                    Player.setWalkSpeed(0.5f);
                    player.setRun(false);
                }
            }
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	
}
