package hu.mudlee;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import hu.mudlee.layers.DebugLayer;
import hu.mudlee.layers.GameLayer;
import hu.mudlee.layers.LayerStack;

public class LD51Game extends ApplicationAdapter {
	public static float gameLoadingProgress = 0;
	private final LayerStack stack = new LayerStack();

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		var assetManager = new AssetManager();
		var inputManager = new InputManager();

		var gameLayer = new GameLayer(assetManager, inputManager);
		stack.addLayer(gameLayer);
		//stack.addLayer(new DebugLayer());

		gameLayer.loadGame();
	}

	@Override
	public void render() {
		stack.render();
	}

	@Override
	public void dispose() {
		stack.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stack.resize(width, height);
	}

	@Override
	public void pause() {
		stack.pause();
	}

	@Override
	public void resume() {
		stack.resume();
	}
}