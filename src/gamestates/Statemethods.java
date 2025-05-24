package gamestates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface Statemethods {
	public void update();

	public void draw(GraphicsContext gc);

	public void mouseClicked(MouseEvent event);

	public void mousePressed(MouseEvent event);

	public void mouseReleased(MouseEvent event);

	public void mouseMoved(MouseEvent event);

	public void keyPressed(KeyEvent event);

	public void keyReleased(KeyEvent event);

}
