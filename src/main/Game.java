package main;

import gamestates.About;
import gamestates.Developers;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import inputs.KeyboardInputs;
import inputs.MouseInputs;


public class Game extends AnimationTimer {
    
	private Canvas canvas;
    private GraphicsContext gc;
    private GameStage gameStage;
    
    // GAME STATES
    private Menu menu;
    private Playing playing;
    private About about;
    private Developers developers;

    // GAME LOOP VARIABLES
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private long lastUpdate = 0;									// The timestamp of the last game logic update.
    private final long updateInterval = 1000000000L / UPS_SET; 		// Time allowed per update for game logic
    private final long frameInterval = 1000000000L / FPS_SET; 		// Time allowed per frame for rendering
    private int frames = 0;											// The number of frames rendered.
    private int updates = 0;										// The number of game logic updates.
    private long lastCheck = System.currentTimeMillis();			// The timestamp of the last frame rate check.
    
    // GAME DIMENSIONS
    public final static int TILES_DEFAULT_SIZE = 32;							// The default size of each tile in pixels.
    public final static float SCALE = 2.0f;										// The scaling factor applied to the default tile size.
    public final static int TILES_IN_WIDTH = 26;								// The number of tiles in the width of the game.
    public final static int TILES_IN_HEIGHT = 14;								// The number of tiles in the height of the game.
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);	// The size of each tile after applying scaling.	
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;			// The width of the game in pixels.
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;			// The height of the game in pixels.
    
    
    public Game(Stage primaryStage) {
        initClasses();
        canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gameStage = new GameStage(primaryStage, canvas, this); 
        setupInputs(); // Set up input handling
        this.open();
    }
	
    // Initialize instances of game classes.
    private void initClasses() {
        // Create instances of game states (Menu, Playing, About, Developers).
        menu = new Menu(this);
        playing = new Playing(this);
        about = new About(this);
        developers = new Developers(this);
    }

    // Opens the game by showing the main game window.
	public void open() {
		gameStage.show();					// Show the game window to start the game.
	}	

	
	/**
	 * Overrides the AnimationTimer's handle method to implement the main game loop.
	 * @param now The timestamp of the current frame.
	 */
	@Override
	public void handle(long now) {
		// Check if it's the first update and initialize the lastUpdate timestamp
		if (lastUpdate == 0) {
		    lastUpdate = now;
		}

		// Calculate the elapsed time since the last update
		long elapsed = now - lastUpdate;

		// Process updates based on the elapsed time and updateInterval
		while (elapsed > updateInterval) {
		    update();                  		// Update the game logic
		    elapsed -= updateInterval; 		// Reduce the elapsed time by the update interval
		    lastUpdate += updateInterval; 	// Update the last update timestamp
		    updates++;                 		// Increment the updates counter
		}

		// Render the game on the JavaFX application thread
		Platform.runLater(() -> render(gc));

		try {
		    // Sleep to control the frame rate and avoid excessive rendering
		    Thread.sleep(Math.max(0, frameInterval - elapsed) / 1000000L);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		// Print frame rate information every second
		if (System.currentTimeMillis() - lastCheck >= 1000) {
		    lastCheck = System.currentTimeMillis();
		    System.out.println("FPS: " + frames + " | UPS: " + updates);
		    frames = 0;    // Reset the frames counter
		    updates = 0;   // Reset the updates counter
		}
		frames++;  // Increment the frames counter

	}

	
	/**
	 * Update method that delegates the update logic based on the current game state.
	 */
	public void update() {
	    switch (Gamestate.state) {
	        case MENU -> menu.update();          // Update logic for the menu state
	        case PLAYING -> playing.update();    // Update logic for the playing state
	        case ABOUT -> about.update();        // Update logic for the about state
	        case DEVELOPERS -> developers.update(); // Update logic for the developers state
	        default -> {
	            // No specific update logic for other states
	        }
	    }
	}


	/**
	 * Render method that delegates the rendering based on the current game state.
	 * @param gc The GraphicsContext used for rendering.
	 */
	public void render(GraphicsContext gc) {
	    switch (Gamestate.state) {
	        case MENU -> menu.draw(gc);          // Render content for the menu state
	        case PLAYING -> playing.draw(gc);    // Render content for the playing state
	        case ABOUT -> about.draw(gc);        // Render content for the about state
	        case DEVELOPERS -> developers.draw(gc); // Render content for the developers state
	        default -> {
	            // No specific rendering for other states
	        }
	    }
	}

	
	
	private void setupInputs() {
        // Create instances of input handlers for keyboard and mouse
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);
        MouseInputs mouseInputs = new MouseInputs(this);

        // Set input handlers for key pressed, key released, mouse moved, and mouse clicked events
        canvas.setOnKeyPressed(event -> keyboardInputs.handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> keyboardInputs.handleKeyRelease(event));
        canvas.setOnMouseMoved(event -> mouseInputs.handleMouseMoved(event));
    	canvas.setOnMouseDragged(event -> mouseInputs.handleMouseDrag(event));
        canvas.setOnMouseClicked(event -> mouseInputs.handleMouseClick(event));
        canvas.setOnMousePressed(event -> mouseInputs.handleMousePressed(event));
        canvas.setOnMouseReleased(event -> mouseInputs.handleMouseReleased(event));

        canvas.setFocusTraversable(true); // Ensure the canvas receives input events
    }
	
	public void windowFocusLost() {
		if (Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}
	
	public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public About getAbout() {
        return about;
    }

    public Developers getDevelopers() {
        return developers;
    }

    public Game getGame() {
        return this;
    }
    
	
//	
//	public void stopGameLoop() {
//	    isRunning = false;
//	}
//	
	
//
//	private void startGameLoop() {
//	    gameThread = new Thread(this);		// Initialize a new thread with this Game instance as the target (implements Runnable).
//	    gameThread.start();					// Start the game loop thread.		
//	}
//
    
//    
//	// The main game loop that runs continuously, updating and rendering the game.
//	@Override
//	public void run() {
//		// Time calculation constants
//		double timePerFrame = 1000000000.0 / FPS_SET;	// Time allowed per frame for rendering
//		double timePerUpdate = 1000000000.0 / UPS_SET;	// Time allowed per update for game logic
//		
//		// Variables for timing and frame rate calculation
//		long previousTime = System.nanoTime();			// Time of the last iteration
//		int frames = 0; 								// Number of frames rendered
//		int updates = 0; 								// Number of game logic updates
//		long lastCheck = System.currentTimeMillis(); 	// Time of the last frame rate check
//		double deltaU = 0; 								// Time accumulator for game logic updates
//		double deltaF = 0;								// Time accumulator for frame rendering
//
//		// Main game loop
//		while (isRunning) {
//			long currentTime = System.nanoTime();
//			
//			// Calculate time since last update and last frame
//			deltaU += (currentTime -  previousTime) / timePerUpdate;
//			deltaF += (currentTime -  previousTime) / timePerFrame;
//			previousTime = currentTime;
//			
//			// Update the game logic if it's time for an update
//			if (deltaU >= 1) {
//                update();
//                updates++;
//                deltaU--;
//            }
//			
//			// Render the game scene if it's time for a frame
//			if (deltaF >= 1) {
////				gc.clearRect(0, 0, getWidth(), getHeight());
//		        
//		        // Render the game content using the GraphicsContext
//				Platform.runLater(() -> render(gc));
//				
////				handle(0);  // Invoke the AnimationTimer's handle method	// Update UI on JavaFX thread
//				frames++;
//				deltaF--;
//			}
//			
//			// Print frame rate information every second
//			if (System.currentTimeMillis() - lastCheck >= 1000) {
//				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
//				frames = 0;
//				updates = 0;
//			}
//		}
//
//	}
//	
	
    
}



