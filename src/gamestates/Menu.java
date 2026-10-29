package gamestates;

import entities.Player;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import levels.LevelManager;
import main.Game;
import ui.UrmButton;

import static utilz.Constants.Menu_UI.T_HEIGHT;
import static utilz.Constants.Menu_UI.T_WIDTH;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class Menu extends State implements Statemethods {

	private LevelManager levelManager;
	private Player player;
	private Image bgImage;
	private Image middleImage;
	private Image titleImage;
	private UrmButton playBtn;
	private UrmButton aboutBtn;
	private UrmButton exitBtn;
	private int xLvlOffset = 0;

	public Menu(Game game) {
		super(game);
		initMenu();
	}

	private void initMenu() {
		levelManager = new LevelManager(game);

		// Get player spawn from level 1
		if (levelManager.getCurrentLevel() != null && levelManager.getCurrentLevel().getPlayerSpawn() != null) {
			javafx.geometry.Point2D spawn = levelManager.getCurrentLevel().getPlayerSpawn();
			player = new Player(
					(float) spawn.getX(),
					(float) spawn.getY(),
					(int) (Player.SPRITE_WIDTH * Game.SCALE),
					(int) (Player.SPRITE_HEIGHT * Game.SCALE),
					null);
		} else {
			player = new Player(200, 200, (int) (Player.SPRITE_WIDTH * Game.SCALE), (int) (Player.SPRITE_HEIGHT * Game.SCALE), null);
		}

		if (levelManager.getCurrentLevel() != null) {
			player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		}
		player.setMenuMode(true);

		bgImage = new Image("images/background/back.png", Game.GAME_WIDTH, Game.GAME_HEIGHT, false, false);
		middleImage = new Image("images/background/middle.png");

		titleImage = new Image("images/menu/title.png", T_WIDTH, T_HEIGHT, false, false);

		// Buttons
		int btnY = (int) (Game.GAME_HEIGHT - URM_SIZE - 34 * Game.SCALE);
		int btnXStart = (int) (29 * Game.SCALE);
		int gap = (int) (20 * Game.SCALE);
		playBtn = new UrmButton(btnXStart, btnY, URM_SIZE, URM_SIZE, 0);
		aboutBtn = new UrmButton(btnXStart + URM_SIZE + gap, btnY, URM_SIZE, URM_SIZE, 1);
		exitBtn = new UrmButton(btnXStart + (URM_SIZE + gap) * 2, btnY, URM_SIZE, URM_SIZE, 2);
	}

	@Override
	public void update() {
		if (playBtn != null) playBtn.update();
		if (aboutBtn != null) aboutBtn.update();
		if (exitBtn != null) exitBtn.update();
		if (player != null) player.update();
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setImageSmoothing(false);

		if (bgImage != null) {
			int bgScale = Game.GAME_HEIGHT / (int) bgImage.getHeight();
			int bgWidth = (int) (bgImage.getWidth() * bgScale);
			int bgHeight = Game.GAME_HEIGHT;
			for (int x = 0; x < Game.GAME_WIDTH; x += bgWidth) {
				gc.drawImage(bgImage, x, 0, bgWidth, bgHeight);
			}
		}

		if (middleImage != null) {
			int middleScale = 3;
			int middleWidth = (int) (middleImage.getWidth() * middleScale);
			int middleHeight = (int) (middleImage.getHeight() * middleScale);
			for (int x = 0; x < Game.GAME_WIDTH; x += middleWidth) {
				gc.drawImage(middleImage, x, 375, middleWidth, middleHeight);
			}
		}

		if (levelManager != null) {
			levelManager.draw(gc, xLvlOffset);
		}

		if (player != null) {
			player.render(gc, xLvlOffset);
		}

		if (titleImage != null) {
			gc.drawImage(titleImage, (Game.GAME_WIDTH - T_WIDTH) / 2, (Game.GAME_HEIGHT - T_HEIGHT) / 100);
		}

		if (playBtn != null) playBtn.draw(gc);
		if (aboutBtn != null) aboutBtn.draw(gc);
		if (exitBtn != null) exitBtn.draw(gc);
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		if (b == null) return false;
		return b.getBounds().contains(game.toLogicalX(e.getX()), game.toLogicalY(e.getY()));
	}

	@Override
	public void mouseClicked(MouseEvent event) {

	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (playBtn != null && isIn(playBtn, event)) {
			playBtn.setMousePressed(true);
		} else if (aboutBtn != null && isIn(aboutBtn, event)) {
			aboutBtn.setMousePressed(true);
		} else if (exitBtn != null && isIn(exitBtn, event)) {
			exitBtn.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (playBtn != null && isIn(playBtn, event) && playBtn.isMousePressed()) {
			if (game.getPlaying() != null) {
				game.getPlaying().resetAll();
			}
			Gamestate.state = Gamestate.PLAYING;
		} else if (aboutBtn != null && isIn(aboutBtn, event) && aboutBtn.isMousePressed()) {
			Gamestate.state = Gamestate.ABOUT;
		} else if (exitBtn != null && isIn(exitBtn, event) && exitBtn.isMousePressed()) {
			Platform.exit();
			System.exit(0);
		}

		if (playBtn != null) playBtn.resetBools();
		if (aboutBtn != null) aboutBtn.resetBools();
		if (exitBtn != null) exitBtn.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (playBtn != null) playBtn.setMouseOver(false);
		if (aboutBtn != null) aboutBtn.setMouseOver(false);
		if (exitBtn != null) exitBtn.setMouseOver(false);

		if (playBtn != null && isIn(playBtn, event)) {
			playBtn.setMouseOver(true);
		} else if (aboutBtn != null && isIn(aboutBtn, event)) {
			aboutBtn.setMouseOver(true);
		} else if (exitBtn != null && isIn(exitBtn, event)) {
			exitBtn.setMouseOver(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {

	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

}
