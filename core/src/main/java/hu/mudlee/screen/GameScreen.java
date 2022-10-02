package hu.mudlee.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import hu.mudlee.*;
import hu.mudlee.conversation.Conversation;
import hu.mudlee.conversation.ConversationItem;
import hu.mudlee.ui.Font;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import static hu.mudlee.Constants.*;

public class GameScreen extends AbstractScreen implements InputProcessor {
	private static final Random rnd = new Random();
	private final Stage stage;
	private final Label agentLabel;
	private final Label neoLabel;
	private final Conversation conversation;
	private final SignalPlayer signalPlayer;
	private final AssetManager assetManager;
	private State state = State.INITIAL_CONVERSATION;
	private double gameStartedAt = System.currentTimeMillis();
	private Music ambient;
	private boolean playingWinConversation;
	private double winningConversationFinishedAt = 0;
	private double nextRandomMumblingAt = 0;

	private enum State {
		INITIAL_CONVERSATION,
		PLAYING_MAIN_SIGNAL,
		GAME_WON,
		GAME_OVER,
	}

	public GameScreen(AssetManager assetManager) {
		conversation = new Conversation(assetManager);
		signalPlayer = new SignalPlayer(assetManager);
		this.assetManager = assetManager;
		var viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(0.5f);
		stage = new Stage(viewport);

		var bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
		bgPixmap.setColor(BACKGROUND_COLOR);
		bgPixmap.fill();
		var textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

		var mainTable = new Table();
		mainTable.setFillParent(true);

		var innerTable = new Table();
		innerTable.setBackground(textureRegionDrawableBg);
		mainTable.add(innerTable).expand().fill().pad(50);

		var titleLabel = new Label("**** NEO'S PRIVATE TERMINAL 2000 SUPER+ EXTRA ****", Styles.standardText(Font.COMMODORE, 12));
		agentLabel = new Label("", Styles.standardText(Font.COMMODORE, 12));
		agentLabel.setAlignment(Align.topLeft);
		neoLabel = new Label("", Styles.standardText(Font.COMMODORE, 12));
		neoLabel.setAlignment(Align.topLeft);

		innerTable.add(titleLabel).expandX().height(70);
		innerTable.row();
		innerTable.add(agentLabel).padTop(20).expand().fill().padLeft(20);
		innerTable.row();
		innerTable.add(neoLabel).padTop(20).expand().fill().padLeft(20);

		stage.addActor(mainTable);

		var inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);

		conversation.play(INITIAL_CONVERSATION, false);
	}

	@Override
	public void show() {
		ambient = assetManager.get(Asset.AUDIO_AMBIENT.getReference(), Music.class);
		ambient.setLooping(true);
		ambient.setVolume(0.1f);
		ambient.play();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(SECONDARY_COLOR);

		if (state != State.GAME_WON && state != State.GAME_OVER) {
			var now = System.currentTimeMillis();
			if ((gameStartedAt + GAME_MAX_LENGTH_MILLIS) < now) {
				setState(State.GAME_OVER);
				conversation.play(GAME_OVER_CONVERSATION, false);
			}
		}

		if (state == State.GAME_OVER && conversation.isOver()) {
			ambient.stop();
			signalPlayer.playGameOverSignalThenClose();
		}

		if (state == State.GAME_WON && !signalPlayer.isPlaying() && !playingWinConversation) {
			conversation.play(WINNING_CONVERSATION, false);
			playingWinConversation = true;
		}

		if (state == State.GAME_WON && playingWinConversation && conversation.isOver() && winningConversationFinishedAt == 0) {
			winningConversationFinishedAt = System.currentTimeMillis();
		}

		if (winningConversationFinishedAt > 0 && (winningConversationFinishedAt + 3000) < System.currentTimeMillis()) {
			Gdx.app.exit();
		}

		if (state == State.INITIAL_CONVERSATION && Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
			conversation.play(Collections.singletonList(INITIAL_CONVERSATION.get(INITIAL_CONVERSATION.size() - 1)), false);
		}

		conversation.tick();

		if (conversation.hasAgentTextChanged()) {
			conversation.ackAgentTextChange();
			agentLabel.setText(conversation.getAgentText());
		}

		if (conversation.hasNeoTextChanged()) {
			conversation.ackNeoTextChange();
			neoLabel.setText(conversation.getNeoText());
		}

		if (conversation.isOver() && state == State.INITIAL_CONVERSATION) {
			setState(State.PLAYING_MAIN_SIGNAL);
			nextRandomMumblingAt = calculateNextRandomMumbling();
			neoLabel.setText(SQUARE_CHAR+"");
			signalPlayer.repeatInitialSequence();
		}

		if (state == State.PLAYING_MAIN_SIGNAL) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ENTER)) {
				parseCommand();
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
				deleteLastChar();
			}

			var now = System.currentTimeMillis();
			if (nextRandomMumblingAt < now && conversation.isOver() && !RANDOM_AGENT_MUMBLINGS.isEmpty()) {
				conversation.play(Collections.singletonList(nextMumbling()), true);
				nextRandomMumblingAt = calculateNextRandomMumbling();
			}
		}

		if (state == State.PLAYING_MAIN_SIGNAL || state == State.GAME_WON || state == State.GAME_OVER) {
			signalPlayer.tick();
		}

		stage.act(delta);
		stage.draw();
	}

	private double calculateNextRandomMumbling() {
		var now = System.currentTimeMillis();
		var next = now + (rnd.nextInt(RANDOM_MUMBLING_DELAY_MAX_MILLIS - RANDOM_MUMBLING_DELAY_MIN_MILLIS) + RANDOM_MUMBLING_DELAY_MIN_MILLIS);
		Log.debug("Next mumbling at: "+(new Date(next))+", now: "+(new Date(now)));
		return next;
	}

	private ConversationItem nextMumbling() {
		Collections.shuffle(RANDOM_AGENT_MUMBLINGS);
		var next = RANDOM_AGENT_MUMBLINGS.get(0);
		RANDOM_AGENT_MUMBLINGS.remove(0);
		return next;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		Styles.dispose();
	}

	@Override
	public boolean keyTyped(char character) {
		if(!Character.isAlphabetic(character) && !Character.isDigit(character) && character != '-' && character != '.' && character != ' ') {
			return true;
		}

		if (state == State.PLAYING_MAIN_SIGNAL) {
			var curr = neoLabel.getText().toString().replace(SQUARE_CHAR+"", "") + character + SQUARE_CHAR;
			neoLabel.setText(curr);
		}
		return true;
	}

	private void parseCommand() {
		var command = neoLabel.getText().toString().replaceAll(" ", "").replaceAll(SQUARE_CHAR+"", "");
		Log.debug("Command: "+command);
		neoLabel.setText(SQUARE_CHAR+"");

		if (command.equals(THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING)) {
			Log.debug("Correct answer");
			signalPlayer.planWinSignalThenClose();
			setState(State.GAME_WON);
			ambient.stop();
		}
		else {
			Log.debug("Invalid answer");
			signalPlayer.playInvalidAnswerSignalThen(Asset.SIGNAL_INITIAL_SOUND);
		}
	}

	private void deleteLastChar() {
		var currentText = neoLabel.getText().toString();
		if (currentText.length() <= 1) {
			return;
		}
		var newText = currentText.substring(0, currentText.length() - 2) + SQUARE_CHAR;
		neoLabel.setText(newText);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	private void setState(State newState) {
		state = newState;
		Log.debug("State changed to "+newState);
	}
}
