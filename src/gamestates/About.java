package gamestates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import levels.LevelManager;
import main.Game;
import ui.KeyboardButton;
import ui.TutorialCat;
import ui.UrmButton;
import utilz.Constants;
import utilz.LoadSave;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class About extends State implements Statemethods {

	private static final int CAT_SIZE = (int) (32 * 7.5);
	private static final int CAT_GAP = 32;
	private static final int CAT_Y = Game.GAME_HEIGHT / 2 - CAT_SIZE;
	private static final int CAT_START_X = (Game.GAME_WIDTH - (4 * CAT_SIZE + 3 * CAT_GAP)) / 2;
	private static final int[] CAT1_REPEATS = {3, 2};
	private static final int[] CAT2_REPEATS = {1, 5};
	private static final int[] CAT3_REPEATS = {2, 1, 2};
	private static final int[] CAT4_REPEATS = {2, 1};
	private static final int CAT_ANI_SPEED = Constants.ANI_SPEED;

	private static final double KB_SCALE = 2.0;

	// WASD d-pad group anchor
	private static final double KB_WASD_X = 360;       // left edge (A key)
	private static final double KB_WASD_Y = 480;       // top edge (W key)
	private static final double KB_WASD_SPACING = 50;  // horizontal gap between A/S/D
	private static final double KB_WASD_ROW_GAP = 45;  // vertical gap from W to A/S/D row

	// Per-key offsets from the WASD anchor (nudge individual keys if needed).
	private static final double KB_W_OFFSET_X = 0;
	private static final double KB_A_OFFSET_X = 0;
	private static final double KB_S_OFFSET_X = 0;
	private static final double KB_D_OFFSET_X = 0;

	// x and y position
	private static final double KB_SHIFT_X = 625;
	private static final double KB_SHIFT_Y = 510;
	private static final double KB_SPACE_X = 888;
	private static final double KB_SPACE_Y = 510;
	private static final double KB_ENTER_X = 1195;
	private static final double KB_ENTER_Y = 495;
	// Per-key animation durations (ticks per state)
	private static final int KB_NORMAL_DEFAULT  = 600;
	private static final int KB_PRESSED_DEFAULT = 800;
	private static final int KB_NORMAL_SHIFT    = 400;
	private static final int KB_PRESSED_SHIFT   = 625;
	private static final int KB_NORMAL_SPACE    = 400;
	private static final int KB_PRESSED_SPACE   = 125;
	private static final int KB_NORMAL_ENTER    = 800;
	private static final int KB_PRESSED_ENTER   = 100;

	private LevelManager levelManager;
	private Image bgImage;
	private Image middleImage;
	private Image tutorialImg;
	private UrmButton closeBtn;
	private TutorialCat[] tutorialCats;
	private KeyboardButton[] keyButtons;
	private int bgX, bgY, bgW, bgH;
	private int xLvlOffset = 0;

	public About(Game game) {
		super(game);
		initOverlay();
	}

	private void initOverlay() {
		levelManager = new LevelManager(game);

		bgImage = new Image("images/background/back.png", Game.GAME_WIDTH, Game.GAME_HEIGHT, false, false);
		middleImage = new Image("images/background/middle.png");

		tutorialImg = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL_IMG);
		bgW = (int) (tutorialImg.getWidth());
		bgH = (int) (tutorialImg.getHeight());
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = Game.GAME_HEIGHT / 2 - bgH / 2 - 15;

		int closeBtnX = Game.GAME_WIDTH / 2 - URM_SIZE / 2;
		int closeBtnY = (int) (Game.GAME_HEIGHT - URM_SIZE - 40 * Game.SCALE);
		closeBtn = new UrmButton(closeBtnX, closeBtnY, URM_SIZE, URM_SIZE, 2);

		tutorialCats = new TutorialCat[] {
			new TutorialCat(new int[]{IDLE_SIT_TAIL_DOWN, WALKING}, CAT1_REPEATS, CAT_ANI_SPEED,
					CAT_START_X, CAT_Y, CAT_SIZE, false),
			new TutorialCat(new int[]{WALKING, RUNNING2}, CAT2_REPEATS, CAT_ANI_SPEED,
					CAT_START_X + (CAT_SIZE + CAT_GAP), CAT_Y, CAT_SIZE, false),
			new TutorialCat(new int[]{IDLE_STAND_TAIL_DOWN, JUMP, FALLING}, CAT3_REPEATS, CAT_ANI_SPEED,
					CAT_START_X + 2 * (CAT_SIZE + CAT_GAP), CAT_Y, CAT_SIZE, false),
			new TutorialCat(new int[]{IDLE, ATTACK2}, CAT4_REPEATS, CAT_ANI_SPEED,
						CAT_START_X + 3 * (CAT_SIZE + CAT_GAP), CAT_Y, CAT_SIZE, false),
		};

		double wasdBtnSize = KB_WASD_SPACING;
		double kbY = KB_WASD_Y;
		double row2Y = KB_WASD_Y + KB_WASD_ROW_GAP;

		keyButtons = new KeyboardButton[] {
			// W, A, S, D
			new KeyboardButton(KB_WASD_X + wasdBtnSize + KB_W_OFFSET_X, kbY, KB_SCALE, LoadSave.KEY_W, KB_NORMAL_DEFAULT, KB_PRESSED_DEFAULT, false),
			new KeyboardButton(KB_WASD_X + KB_A_OFFSET_X, row2Y, KB_SCALE, LoadSave.KEY_A, KB_NORMAL_DEFAULT, KB_PRESSED_DEFAULT, false),
			new KeyboardButton(KB_WASD_X + wasdBtnSize + KB_S_OFFSET_X, row2Y, KB_SCALE, LoadSave.KEY_S, KB_NORMAL_DEFAULT, KB_PRESSED_DEFAULT, false),
			new KeyboardButton(KB_WASD_X + 2 * wasdBtnSize + KB_D_OFFSET_X, row2Y, KB_SCALE, LoadSave.KEY_D, KB_NORMAL_DEFAULT, KB_PRESSED_DEFAULT, true),
			// Shift, Space, Enter
			new KeyboardButton(KB_SHIFT_X, KB_SHIFT_Y, KB_SCALE, LoadSave.KEY_SHIFT, KB_NORMAL_SHIFT, KB_PRESSED_SHIFT, true),
			new KeyboardButton(KB_SPACE_X, KB_SPACE_Y, KB_SCALE, LoadSave.KEY_SPACE, KB_NORMAL_SPACE, KB_PRESSED_SPACE, true),
			new KeyboardButton(KB_ENTER_X, KB_ENTER_Y, KB_SCALE, LoadSave.KEY_ENTER, KB_NORMAL_ENTER, KB_PRESSED_ENTER, true),
		};
	}

	@Override
	public void update() {
		if (closeBtn != null) closeBtn.update();
		if (tutorialCats != null) {
			for (TutorialCat cat : tutorialCats)
				cat.update();
		}
		if (keyButtons != null) {
			for (KeyboardButton btn : keyButtons)
				btn.update();
		}
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

		gc.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.78));
		gc.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		if (tutorialImg != null) {
			gc.drawImage(tutorialImg, bgX, bgY, bgW, bgH);
		}

		if (tutorialCats != null) {
			for (TutorialCat cat : tutorialCats)
				cat.draw(gc);
		}

		if (keyButtons != null) {
			for (KeyboardButton btn : keyButtons)
				btn.draw(gc);
		}

		if (closeBtn != null) closeBtn.draw(gc);
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
		if (isIn(closeBtn, event)) closeBtn.setMousePressed(true);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (isIn(closeBtn, event) && closeBtn.isMousePressed()) {
			Gamestate.state = Gamestate.MENU;
		}
		if (closeBtn != null) closeBtn.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (closeBtn != null) closeBtn.setMouseOver(false);
		if (isIn(closeBtn, event)) closeBtn.setMouseOver(true);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getCode()) {
			case B, ESCAPE, BACK_SPACE -> {
				Gamestate.state = Gamestate.MENU;
			}
			default -> {}
		}
	}

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
