package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.Game;
import utilz.Constants;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.SLEEP2;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {

    private static final int CAT_SIZE = (int) (32 * 10);

    private Playing playing;
    private Image gameOverImg;
    private TutorialCat sleepCat;
    private UrmButton menuBtn;
    private int bgX, bgY, bgW, bgH;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        initOverlay();
    }

    private void initOverlay() {
        gameOverImg = LoadSave.GetSpriteAtlas(LoadSave.GAMEOVER_IMG);
        if (gameOverImg != null) {
            bgW = (int) gameOverImg.getWidth();
            bgH = (int) gameOverImg.getHeight();
            bgX = Game.GAME_WIDTH / 2 - bgW / 2;
            bgY = Game.GAME_HEIGHT / 2 - bgH / 2 - 15;
        }

        int catX = Game.GAME_WIDTH / 2 - CAT_SIZE / 2 - 20;
        int catY = bgY + 10;
        sleepCat = new TutorialCat(new int[]{SLEEP2}, new int[]{1}, Constants.ANI_SPEED, catX, catY, CAT_SIZE, false);

        int menuBtnX = Game.GAME_WIDTH / 2 - URM_SIZE / 2;
        int menuBtnY = (int) (Game.GAME_HEIGHT - URM_SIZE - 40 * Game.SCALE);
        menuBtn = new UrmButton(menuBtnX, menuBtnY, URM_SIZE, URM_SIZE, 2);
    }

    public void update() {
        if (sleepCat != null)
            sleepCat.update();
        if (menuBtn != null)
            menuBtn.update();
    }

    public void draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);

        gc.setFill(Color.rgb(0, 0, 0, 0.78));
        gc.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        if (gameOverImg != null)
            gc.drawImage(gameOverImg, bgX, bgY, bgW, bgH);

        if (sleepCat != null)
            sleepCat.draw(gc);
        if (menuBtn != null)
            menuBtn.draw(gc);
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        if (b == null) return false;
        return b.getBounds().contains(playing.getGame().toLogicalX(e.getX()), playing.getGame().toLogicalY(e.getY()));
    }

    public void mousePressed(MouseEvent event) {
        if (isIn(menuBtn, event))
            menuBtn.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent event) {
        if (isIn(menuBtn, event) && menuBtn.isMousePressed()) {
            playing.resetToFirstLevel();
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
        if (menuBtn != null)
            menuBtn.resetBools();
    }

    public void mouseMoved(MouseEvent event) {
        if (menuBtn != null)
            menuBtn.setMouseOver(false);
        if (isIn(menuBtn, event))
            menuBtn.setMouseOver(true);
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            // Reset game state and transition to the main menu
            playing.resetToFirstLevel();
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
