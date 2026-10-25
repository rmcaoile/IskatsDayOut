package main;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameStage {

	private Stage primaryStage;
    private Canvas canvas;
    private Game game;

	public GameStage(Stage primaryStage, Canvas canvas, Game game) {
        this.primaryStage = primaryStage;
        this.canvas = canvas;
        this.game = game;
        initWindow();
    }

	// Sets up the primary stage (main window) properties
	private void initWindow() {
        primaryStage.setTitle("Game Window");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Game.GAME_WIDTH / 2.0);
        primaryStage.setMinHeight(Game.GAME_HEIGHT / 2.0);

        setupScene();
        primaryStage.sizeToScene();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        setupFocusListener();
    }
	
	private void setupFocusListener() {
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                game.windowFocusLost(); // Call windowFocusLost() from GameTimer
            } else {
                // Window gained focus
            }
        });
    }


	// Sets up the root layout with the game scene and displays it
	private void setupScene() {
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        primaryStage.setScene(scene);
    }


	public void show() {
		primaryStage.show();
	}
}


