package hu.mudlee.layers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import hu.mudlee.Asset;
import hu.mudlee.InputManager;
import hu.mudlee.LD51Game;
import hu.mudlee.Log;
import hu.mudlee.screen.GameScreen;
import hu.mudlee.screen.LoadingScreen;

import java.util.Arrays;

public class GameLayer extends Game implements Layer {
	private final AssetManager assetManager;
	private final InputManager inputManager; // TODO: nem kell
	private boolean assetsLoaded;
	private Music ambient;

	public GameLayer(AssetManager assetManager, InputManager inputManager) {
		this.assetManager = assetManager;
		this.inputManager = inputManager;
	}

	@Override
	public void create() {
		Gdx.input.setInputProcessor(inputManager.getMultiplexer());
	}

	@Override
	public void render() {
		super.render();

		if (!assetsLoaded) {
			var loadingScreen = (LoadingScreen) screen;
			if (assetManager.update() && !loadingScreen.isAnimating()) {
				assetsLoaded = true;
				ambient = assetManager.get(Asset.AUDIO_AMBIENT.getReference(), Music.class);
				ambient.setLooping(true);
				ambient.setVolume(0.1f);
				ambient.play();
				startGame();
				Log.debug("Assets loaded");
			}

			LD51Game.gameLoadingProgress = assetManager.getProgress();
		}
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO
		//System.gc();
		Log.debug("Switching screen to " + screen.getClass().getName());
		super.setScreen(screen);
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.dispose();
	}

	public void loadGame() {
		setScreen(new LoadingScreen());
		Arrays.stream(Asset.values()).forEach(asset -> {
			assetManager.load(asset.getReference(), asset.getType());
			Log.debug("Loading asset "+asset.getReference());
		});
	}

	public void startGame() {
		Log.debug("Game started");
		setScreen(new GameScreen(assetManager));
	}
}
