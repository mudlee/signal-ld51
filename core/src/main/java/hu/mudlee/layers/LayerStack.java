package hu.mudlee.layers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class LayerStack implements ApplicationListener, Disposable {
	private final Array<Layer> layers = new Array<>();

	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	@Override
	public void create() {
		for (Layer layer : layers) {
			layer.create();
		}
	}

	@Override
	public void resize(int width, int height) {
		for (Layer layer : layers) {
			layer.resize(width, height);
		}
	}

	@Override
	public void render() {
		for (Layer layer : layers) {
			layer.render();
		}
	}

	@Override
	public void pause() {
		for (Layer layer : layers) {
			layer.pause();
		}
	}

	@Override
	public void resume() {
		for (Layer layer : layers) {
			layer.resume();
		}
	}

	@Override
	public void dispose() {
		for (Layer layer : layers) {
			layer.dispose();
		}
	}
}
