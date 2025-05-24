package gamestates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.Game;

import static utilz.Constants.Menu_UI.*;

public class Menu extends State implements Statemethods{

	public Menu(Game game) {
		super(game);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
    public void draw(GraphicsContext gc) {
		Image bg = new Image("images/menu/background.png", Game.GAME_WIDTH, Game.GAME_HEIGHT, false, false);
		gc.drawImage(bg, 0, 0);

		Image title = new Image("images/menu/title.png", T_WIDTH, T_HEIGHT, false, false);
		gc.drawImage(title, (Game.GAME_WIDTH-T_WIDTH)/2, (Game.GAME_HEIGHT-T_HEIGHT)/4);


		Image cat = new Image("images/menu/cat.png", C_WIDTH, C_HEIGHT, false, false);
		gc.drawImage(cat, (double) Game.GAME_WIDTH/4*2, (Game.GAME_HEIGHT-C_HEIGHT)-30);


		Image buttons = new Image("images/menu/buttons.png", B_WIDTH, B_HEIGHT, false, false);
		gc.drawImage(buttons, 10, (Game.GAME_HEIGHT-B_HEIGHT)-20);
    }

	@Override
	public void mouseClicked(MouseEvent event) {

	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getCode()){
			case ENTER, SPACE, P -> {
				System.out.println("Game Starting...");
				Gamestate.state = Gamestate.PLAYING;
			}
			case A -> {
				System.out.println("About Loading....");
				Gamestate.state = Gamestate.ABOUT;
			}
			case D -> {
				System.out.println("Developers Loading....");
				Gamestate.state = Gamestate.DEVELOPERS;
			}
			default -> {}
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

}
