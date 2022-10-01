package hu.mudlee.layers;

import com.badlogic.gdx.ApplicationListener;

public interface Layer extends ApplicationListener {
	@Override
	default void create() {}

	@Override
	default void resize(int width, int height) {}

	@Override
	default void render() {}

	@Override
	default void pause() {}

	@Override
	default void resume() {}

	@Override
	default void dispose() {}
}
