package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * Represents a keyboard key button used in the About/tutorial screen.
 * The button automatically cycles between the normal and pressed states,
 * staying in each state for a configurable number of update ticks.
 */
public class KeyboardButton {

    private final double x, y;
    private final double scale;
    private final int frameWidth, frameHeight;
    private Image normalFrame;
    private Image pressedFrame;
    private boolean pressed = false;
    private int animTick = 0;
    private final int normalDuration;
    private final int pressedDuration;
    private final boolean autoBlink;

    /**
     * @param x               x-coordinate of the button
     * @param y               y-coordinate of the button
     * @param scale           scale factor applied to the button
     * @param imagePath       path to the 1x2 horizontal strip image
     * @param normalDuration  ticks to stay in the normal (unpressed) state
     * @param pressedDuration ticks to stay in the pressed state
     * @param autoBlink       cycles between normal and pressed automatically. 
     */
    public KeyboardButton(double x, double y, double scale, String imagePath,
                          int normalDuration, int pressedDuration, boolean autoBlink) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.normalDuration = Math.max(1, normalDuration);
        this.pressedDuration = Math.max(1, pressedDuration);
        this.autoBlink = autoBlink;

        Image sprite = loadSprite(imagePath);
        if (sprite != null) {
            int w = (int) sprite.getWidth();
            int h = (int) sprite.getHeight();
            this.frameWidth = w / 2;
            this.frameHeight = h;

            PixelReader reader = sprite.getPixelReader();
            if (reader != null && frameWidth > 0 && frameHeight > 0) {
                normalFrame = new WritableImage(reader, 0, 0, frameWidth, frameHeight);
                pressedFrame = new WritableImage(reader, frameWidth, 0, frameWidth, frameHeight);
            }
        } else {
            this.frameWidth = 0;
            this.frameHeight = 0;
        }
    }

    private Image loadSprite(String imagePath) {
        try {
            Image sprite = new Image(imagePath);
            if (sprite != null && sprite.getPixelReader() != null) {
                return sprite;
            }
            System.err.println("KeyboardButton: failed to load " + imagePath);
        } catch (Exception e) {
            System.err.println("KeyboardButton: error loading " + imagePath);
            e.printStackTrace();
        }
        return null;
    }

    public void update() {
        if (!autoBlink) {
            return;
        }
        animTick++;
        if (pressed) {
            if (animTick >= pressedDuration) {
                animTick = 0;
                pressed = false;
            }
        } else {
            if (animTick >= normalDuration) {
                animTick = 0;
                pressed = true;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        Image frame = pressed ? pressedFrame : normalFrame;
        if (frame != null) {
            gc.drawImage(frame, x, y, frameWidth * scale, frameHeight * scale);
        }
    }
}