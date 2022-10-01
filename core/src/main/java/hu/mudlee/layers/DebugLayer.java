package hu.mudlee.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class DebugLayer implements Layer {
	private long lastTimeCounted;
	private float sinceChange;
	private float frameRate;
	private final BitmapFont font;
	private final SpriteBatch batch;
	private final OrthographicCamera camera;

	public DebugLayer() {
		lastTimeCounted = TimeUtils.millis();
		sinceChange = 0;
		frameRate = Gdx.graphics.getFramesPerSecond();
		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void resize(int screenWidth, int screenHeight) {
		camera.viewportWidth = screenWidth;
		camera.viewportHeight = screenHeight;
		camera.position.x = screenWidth / 2f;
		camera.position.y = screenHeight / 2f;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render() {
		update();
		batch.begin();
		font.draw(batch, "FPS: " + (int)frameRate, 10, Gdx.graphics.getHeight() - 10);
		font.draw(batch, "Java Heap: " + asMByte(Gdx.app.getJavaHeap()) + "MB", 10, Gdx.graphics.getHeight() - 30);
		font.draw(batch, "Native Heap: " + asMByte(Gdx.app.getNativeHeap()) + "MB", 10, Gdx.graphics.getHeight() - 50);
		batch.end();
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
	}

	private int asMByte(long bytes) {
		return ((Long)(bytes / 1024L / 1024L)).intValue();
	}

	private void update() {
		long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
		lastTimeCounted = TimeUtils.millis();

		sinceChange += delta;
		if(sinceChange >= 1000) {
			sinceChange = 0;
			frameRate = Gdx.graphics.getFramesPerSecond();
		}
	}
}
