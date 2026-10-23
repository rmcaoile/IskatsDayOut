package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

/**
 * Represents an overlay displayed when the game is paused, providing buttons for menu and resume.
 */
public class PausedOverlay {

    private Playing playing;
    private UrmButton menu, resume;
    private Image img;
    private int bgX, bgY, bgW, bgH;

    private static final int MENU_BUTTON_X = (int) (330 * Game.SCALE);
    private static final int RESUME_BUTTON_X = (int) (445 * Game.SCALE);
    private static final int BUTTON_Y = (int) (195 * Game.SCALE);


    /**
     * Creates a new PausedOverlay associated with the given Playing game state.
     * @param playing The Playing game state.
     */
    public PausedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    /**
     * Initializes the buttons for the overlay.
     */
    private void initButtons() {
        resume = new UrmButton(RESUME_BUTTON_X, BUTTON_Y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(MENU_BUTTON_X, BUTTON_Y, URM_SIZE, URM_SIZE, 2);
    }

    /**
     * Initializes the image for the overlay background.
     */
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.PAUSED_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    /**
     * Draws the overlay on the specified GraphicsContext.
     * @param gc The GraphicsContext on which to draw the overlay.
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.78));
        gc.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        gc.drawImage(img, bgX, bgY, bgW, bgH);
        resume.draw(gc);
        menu.draw(gc);
    }

    /**
     * Updates the overlay, particularly the state of its buttons.
     */
    public void update() {
        resume.update();
        menu.update();
    }

    /**
     * Checks if the mouse is over a button and updates their state accordingly.
     * @param e The MouseEvent representing the mouse movement.
     */
    public void mouseMoved(MouseEvent e) {
        resume.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(resume, e))
            resume.setMouseOver(true);
    }

    /**
     * Handles mouse release events and performs corresponding actions.
     * @param e The MouseEvent representing the mouse release.
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.resetToFirstLevel();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(resume, e)) {
            if (resume.isMousePressed()) {
                playing.setPaused(false);
            }
        }

        menu.resetBools();
        resume.resetBools();
    }

    /**
     * Handles mouse press events and updates the state of the pressed button.
     * @param e The MouseEvent representing the mouse press.
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(resume, e))
            resume.setMousePressed(true);
    }

    /**
     * Checks if the given UrmButton contains the specified mouse coordinates.
     * @param b The UrmButton to check.
     * @param e The MouseEvent representing the mouse coordinates.
     * @return True if the mouse coordinates are within the bounds of the button, false otherwise.
     */
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
