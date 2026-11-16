package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import utilz.Constants;
import utilz.LoadSave;

public class TutorialCat {

	private static final int FRAME_SIZE = 32;
	private static Image[][] frames;

	private final int[] sequence;
	private final int[] repeatCounts;
	private final int aniSpeed;
	private final double x, y, size;
	private final boolean flipX;

	private int seqIndex = 0;
	private int repeatCount = 0;
	private int state;
	private int aniTick = 0;
	private int aniIndex = 0;

	public TutorialCat(int[] sequence, int[] repeatCounts, int aniSpeed, double x, double y, double size, boolean flipX) {
		this.sequence = sequence.clone();
		this.repeatCounts = new int[this.sequence.length];
		for (int i = 0; i < this.sequence.length; i++) {
			int repeat = (repeatCounts != null && i < repeatCounts.length) ? repeatCounts[i] : 1;
			this.repeatCounts[i] = Math.max(1, repeat);
		}
		this.aniSpeed = Math.max(1, aniSpeed);
		this.x = x;
		this.y = y;
		this.size = size;
		this.flipX = flipX;
		this.state = sequence[0];
		loadFrames();
	}

	private static synchronized void loadFrames() {
		if (frames != null)
			return;

		Image atlas = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		if (atlas == null || atlas.getPixelReader() == null) {
			System.err.println("TutorialCat: failed to load " + LoadSave.PLAYER_ATLAS);
			return;
		}

		PixelReader reader = atlas.getPixelReader();
		frames = new Image[51][32];
		for (int row = 0; row < frames.length; row++) {
			for (int col = 0; col < frames[row].length; col++) {
				frames[row][col] = new WritableImage(reader, col * FRAME_SIZE, row * FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
			}
		}
	}

	public void update() {
		aniTick++;
		if (aniTick < aniSpeed)
			return;

		aniTick = 0;
		aniIndex++;

		if (aniIndex < Constants.PlayerConstants.GetSpriteAmount(state))
			return;

		aniIndex = 0;
		repeatCount++;
		if (repeatCount < repeatCounts[seqIndex])
			return;

		repeatCount = 0;
		seqIndex = (seqIndex + 1) % sequence.length;
		state = sequence[seqIndex];
	}

	public void draw(GraphicsContext gc) {
		if (frames == null)
			return;

		int frameCount = Constants.PlayerConstants.GetSpriteAmount(state);
		int index = Math.min(aniIndex, frameCount - 1);
		double drawX = flipX ? x + size : x;
		double drawW = flipX ? -size : size;

		gc.drawImage(frames[state][index], 0, 0, FRAME_SIZE, FRAME_SIZE, drawX, y, drawW, size);
	}
}