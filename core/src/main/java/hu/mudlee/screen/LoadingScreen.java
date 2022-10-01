package hu.mudlee.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import hu.mudlee.LD51Game;
import hu.mudlee.GfxUtil;

import static hu.mudlee.Constants.*;

public class LoadingScreen extends AbstractScreen{
	private Stage stage;
	private ProgressBar progressBar;

	public boolean isAnimating() {
		return progressBar.isAnimating();
	}

	@Override
	public void show() {
		final var viewport = new ScreenViewport();
		stage = new Stage(viewport);

		final var width = Gdx.graphics.getWidth() / 2;
		final var height = 50;

		final var style = new ProgressBar.ProgressBarStyle();
		style.background = GfxUtil.createBg(width, height, SECONDARY_COLOR);
		style.knob = GfxUtil.createBg(0, height, LOADER_COLOR);
		style.knobBefore = GfxUtil.createBg(width, height, LOADER_COLOR);

		progressBar = new ProgressBar(0f, 1f, 0.01f, false, style);
		progressBar.setValue(0f);
		progressBar.setAnimateDuration(0.25f);
		progressBar.setWidth(width);
		progressBar.setHeight(height);

		final var cont = new Container<>(progressBar);
		cont.width((float) width);

		final var rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.add(cont);
		stage.addActor(rootTable);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(BACKGROUND_COLOR);

		progressBar.setValue(LD51Game.gameLoadingProgress);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
