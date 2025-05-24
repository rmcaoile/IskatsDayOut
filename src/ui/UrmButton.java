package ui;

import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.URM_DEFAULT_SIZE;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * Represents a button with different states (normal, hover, pressed) used in the user interface.
 * Extends the PauseButton class.
 */
public class UrmButton extends PauseButton {
    private Image[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    
    /**
     * Constructs a UrmButton with the specified position, dimensions, and row index.
     * @param x         The x-coordinate of the button.
     * @param y         The y-coordinate of the button.
     * @param width     The width of the button.
     * @param height    The height of the button.
     * @param rowIndex  The row index for determining the button's appearance in the sprite atlas.
     */
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    
    /**
     * Loads the button images from the sprite atlas.
     */
    private void loadImgs() {
        Image temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new Image[3];

        if (temp == null) {
            System.err.println("Error: Unable to load sprite atlas.");
            // Handle the error or return from the method
            return;
        }

        for (int i = 0; i < imgs.length; i++) {
            int startX = i * URM_DEFAULT_SIZE;
            int startY = rowIndex * URM_DEFAULT_SIZE;

            // Check if the specified region is within the image bounds
            if (startX < temp.getWidth() && startY < temp.getHeight()) {
                imgs[i] = new WritableImage(temp.getPixelReader(), startX, startY, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
            } else {
                System.err.println("Error: Invalid sprite atlas region.");
            }
        }
    }

    
    /**
     * Updates the button's index based on its state (normal, hover, pressed).
     */
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    
    /**
     * Draws the button on the specified GraphicsContext.
     * @param gc The GraphicsContext on which to draw the button.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE);
    }

    
    /**
     * Resets the mouseOver and mousePressed boolean values to false.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    
    public boolean isMouseOver() {
        return mouseOver;
    }
    
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }


}


