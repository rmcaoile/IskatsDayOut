package gamestates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.Game;

import static utilz.Constants.Menu_UI.*;
import static utilz.Constants.Menu_UI.C_HEIGHT;

public class Developers extends State implements Statemethods{
    public Developers(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        Image bg = new Image("images/menu/background.png", Game.GAME_WIDTH, Game.GAME_HEIGHT, false, false);
        gc.drawImage(bg, 0, 0);

        Image cat = new Image("images/menu/cat.png", C_WIDTH, C_HEIGHT, false, false);
        gc.drawImage(cat, (double) Game.GAME_WIDTH/4*2, (Game.GAME_HEIGHT-C_HEIGHT)-30 );

        Image back = new Image("images/menu/back.png", BK_WIDTH, BK_HEIGHT, false, false);
        gc.drawImage(back, 10, (Game.GAME_HEIGHT-BK_HEIGHT)-20);

        Image devs = new Image("images/menu/developers.png", D_WIDTH, D_HEIGHT, false, false);
        gc.drawImage(devs, (double) Game.GAME_WIDTH/8, 130);
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
	    	case B, ESCAPE, BACK_SPACE -> {
	    		System.out.println("Menu Loading...");
	    		Gamestate.state = Gamestate.MENU;
	    	}
			default -> {}
    	}
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
