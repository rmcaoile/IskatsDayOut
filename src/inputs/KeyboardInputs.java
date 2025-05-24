package inputs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import main.Game;

import gamestates.Gamestate;


public class KeyboardInputs implements EventHandler<KeyEvent> {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent event) {
    	handleKeyPressed(event);
    	handleKeyRelease(event);

    }
    
    public void handleKeyPressed(KeyEvent event) {
        switch (Gamestate.state) {
            case MENU -> game.getMenu().keyPressed(event);
            case PLAYING -> game.getPlaying().keyPressed(event);
			case ABOUT -> game.getAbout().keyPressed(event);
			case DEVELOPERS -> game.getDevelopers().keyPressed(event);
            default -> {}
        }
 	}
    
    public void handleKeyRelease(KeyEvent event) {
        switch (Gamestate.state) {
            case MENU -> game.getMenu().keyReleased(event);
            case PLAYING -> game.getPlaying().keyReleased(event);
			case ABOUT -> game.getAbout().keyReleased(event);
			case DEVELOPERS -> game.getDevelopers().keyReleased(event);
            default -> {}
        }
	}
    
    
}



