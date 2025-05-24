package inputs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import gamestates.Gamestate;
import main.Game;

public class MouseInputs implements EventHandler<MouseEvent> {

    private Game game;

    public MouseInputs(Game game) {
        this.game = game;
    }

    @Override
    public void handle(MouseEvent event) {
    	handleMouseMoved(event);
    	handleMouseDrag(event);
        handleMouseClick(event);
        handleMousePressed(event);
        handleMouseReleased(event);
    }

    public void handleMouseMoved(MouseEvent event) {
    	switch (Gamestate.state) {
    	case MENU:	
    		game.getMenu().mouseMoved(event);
    		break;
    	case PLAYING:
    		game.getPlaying().mouseMoved(event);
    		break;
    	default:
    		break;
    	}
    }

    public void handleMouseDrag(MouseEvent event) {
        switch (Gamestate.state) {
            case PLAYING:
            	game.getPlaying().mouseDragged(event);
                break;
            default:
                break;
        }
    }
    
    public void handleMouseClick(MouseEvent event) {
        switch (Gamestate.state) {
            case PLAYING:
            	game.getPlaying().mouseClicked(event);
                break;
            default:
                break;
        }
    }

    public void handleMousePressed(MouseEvent event) {
    	switch (Gamestate.state) {
	        case MENU:
	        	game.getMenu().mousePressed(event);
	            break;
	        case PLAYING:
	        	game.getPlaying().mousePressed(event);
	            break;
	        default:
	            break;
    	}
    }

    public void handleMouseReleased(MouseEvent event) {
    	switch (Gamestate.state) {
	        case MENU:
	        	game.getMenu().mouseReleased(event);
	            break;
	        case PLAYING:
	        	game.getPlaying().mouseReleased(event);
	            break;
	        default:
	            break;
    	}
    }

}

