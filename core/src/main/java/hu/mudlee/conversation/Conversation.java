package hu.mudlee.conversation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.StringBuilder;
import hu.mudlee.Asset;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static hu.mudlee.Constants.*;

public class Conversation {
	private static final Random rnd = new Random();
	private final AssetManager assetManager;
	private final StringBuilder agentText = new StringBuilder();
	private final StringBuilder neoText = new StringBuilder();
	private boolean agentTextChanged = true;
	private boolean neoTextChanged = true;
	private int agentTypingIdx = 0;
	private int neoTypingIdx = 0;
	private double agentLastTyped = 0;
	private double neoLastTyped = 0;
	private double lastQAFinishedAt = 0;
	private String agentTextToDisplay = "";
	private String neoTextToDisplay = "";
	private final Queue<ConversationItem> queue = new LinkedBlockingQueue<>();
	private final List<Sound> neoKeyboardSounds = new ArrayList<>();
	private final List<Sound> agentKeyboardSounds = new ArrayList<>();

	public Conversation(AssetManager assetManager) {
		this.assetManager = assetManager;
		neoKeyboardSounds.add(assetManager.get(Asset.KEY_1.getReference(), Sound.class));
		neoKeyboardSounds.add(assetManager.get(Asset.KEY_2.getReference(), Sound.class));
		neoKeyboardSounds.add(assetManager.get(Asset.KEY_3.getReference(), Sound.class));
		neoKeyboardSounds.add(assetManager.get(Asset.KEY_4.getReference(), Sound.class));

		agentKeyboardSounds.add(assetManager.get(Asset.A_KEY_1.getReference(), Sound.class));
		agentKeyboardSounds.add(assetManager.get(Asset.A_KEY_2.getReference(), Sound.class));
		agentKeyboardSounds.add(assetManager.get(Asset.A_KEY_3.getReference(), Sound.class));
		agentKeyboardSounds.add(assetManager.get(Asset.A_KEY_4.getReference(), Sound.class));
	}

	public void play(List<ConversationItem> qas, boolean onlyAgent) {
		agentTextChanged = true;
		agentText.clear();
		agentTypingIdx = 0;
		agentLastTyped = 0;
		agentTextToDisplay = "";
		queue.clear();
		queue.addAll(qas);
		agentLastTyped = System.currentTimeMillis();

		if (!onlyAgent) {
			neoTextChanged = true;
			neoText.clear();
			neoTypingIdx = 0;
			neoLastTyped = 0;
			neoTextToDisplay = "";
			neoLastTyped = System.currentTimeMillis();
		}
	}

	public void tick() {
		double now = System.currentTimeMillis();

		// When a QA finished but still have something in the queue
		if(!queue.isEmpty() && agentTextToDisplay.isEmpty() && neoTextToDisplay.isEmpty() && (lastQAFinishedAt + SPACE_BETWEEN_CONVERSATION_PARTS) < now) {
			var latest = queue.poll();
			agentTextToDisplay = latest.agent();
			resetAgentText();

			if (!latest.onlyAgent()) {
				neoTypingIdx = 0;
				neoTextToDisplay = latest.neo();
			}
		}

		if(agentTextToDisplay.isEmpty() && neoTextToDisplay.isEmpty()) {
			return;
		}

		// Neo talks (when agent is finished his speech), but only after a short wait
		if (agentTextToDisplay.isEmpty() && (agentLastTyped + SPACE_BETWEEN_CONVERSATION_Q_A_MILLIS) < now) {
			if (throttleTyping(neoLastTyped, now)) {
				neoTypes(now);
			}
		}

		// Agent talks
		if (!agentTextToDisplay.isEmpty()) {
			if (throttleTyping(agentLastTyped, now)) {
				agentTypes(now);
			}
		}
	}

	public boolean hasAgentTextChanged() {
		return agentTextChanged;
	}

	public void ackAgentTextChange() {
		agentTextChanged = false;
	}

	public String getAgentText() {
		return agentText.toString();
	}

	public boolean hasNeoTextChanged() {
		return neoTextChanged;
	}

	public void ackNeoTextChange() {
		neoTextChanged = false;
	}

	public String getNeoText() {
		return neoText.toString() + SQUARE_CHAR;
	}

	public boolean isOver() {
		return queue.isEmpty() && agentTextToDisplay.isEmpty() && neoTextToDisplay.isEmpty();
	}

	private void resetAgentText() {
		agentText.clear();
		agentTextChanged = true;
		agentTypingIdx = 0;
	}

	private boolean throttleTyping(double lastTypedAt, double now) {
		return (lastTypedAt + TYPING_FREQUENCY_MILLIS) < now && (rnd.nextInt(10 - 1) + 1) > 7;
	}

	private void neoTypes(double now) {
		if (neoTypingIdx == 0) {
			// Neo just starts typing a new shit
			neoText.clear();
		}
		neoLastTyped = now;
		neoText.append(neoTextToDisplay.charAt(neoTypingIdx));
		neoKeyboardSounds.get(rnd.nextInt(neoKeyboardSounds.size())).play(0.2f);
		neoTextChanged = true;
		neoTypingIdx++;
		if (neoTypingIdx > neoTextToDisplay.length() - 1) {
			neoTextToDisplay = "";
			lastQAFinishedAt = now;
		}
	}

	private void agentTypes(double now) {
		agentLastTyped = now;
		agentText.append(agentTextToDisplay.charAt(agentTypingIdx));
		agentTextChanged = true;
		agentKeyboardSounds.get(rnd.nextInt(agentKeyboardSounds.size())).play(0.01f);
		agentTypingIdx++;
		if (agentTypingIdx > agentTextToDisplay.length() - 1) {
			agentTextToDisplay = "";
		}
	}
}
