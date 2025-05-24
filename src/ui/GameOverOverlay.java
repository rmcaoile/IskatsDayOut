package ui;

import gamestates.Playing;
import gamestates.Gamestate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Game;

public class GameOverOverlay {

    private Playing playing;

    // Constructor
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    // Draw method to display the game over overlay
    public void draw(GraphicsContext gc) {
        // Draw a semi-transparent black background
        gc.setFill(new Color(0, 0, 0, 0.8));
        gc.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // Set text properties
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));
        gc.setTextAlign(TextAlignment.CENTER);

        // Display game over message and additional information
        gc.fillText("Game Over", Game.GAME_WIDTH / 2, 150);
        gc.fillText("Noooo! Iskat Died!!", Game.GAME_WIDTH / 2, 300);
        gc.fillText("Press ESC to enter Main Menu", Game.GAME_WIDTH / 2, 450);
    }

    // Handle key press events, specifically the ESC key to return to the main menu
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            // Reset game state and transition to the main menu
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
