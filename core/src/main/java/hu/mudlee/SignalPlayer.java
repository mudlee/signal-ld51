package hu.mudlee;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static hu.mudlee.Constants.SIGNAL_REPEAT_FREQUENCY_MILLIS;

public class SignalPlayer {
    private record SignalQueueEntry(Asset morse, boolean loop){}

    private final AssetManager assetManager;
    private Sound morse;
    private Queue<SignalQueueEntry> queue = new LinkedBlockingQueue<>();
    private String currentMorse = "";
    private double lastPlayed = 0;

    public SignalPlayer(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void tick() {
        if(queue.isEmpty()) {
            return;
        }

        double now = System.currentTimeMillis();
        if ((lastPlayed + SIGNAL_REPEAT_FREQUENCY_MILLIS) < now || lastPlayed == 0) {
            var next = queue.poll();
            lastPlayed = now;
            playMorse(next.morse);
            if (next.loop) {
                var old = queue.stream().toList();
                queue.clear();
                queue.add(next);
                queue.addAll(old);
            }
        }
    }

    public void repeatInitialSequence() {
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INITIAL_SOUND, true));
    }

    public void repeatInvalidAnswerMorseThreeTimesThenRepeat(Asset asset) {
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INVALID_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INVALID_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INVALID_SOUND, false));
        queue.add(new SignalQueueEntry(asset, true));
    }

    public void repeatWinSignalThreeTimesThenClose() {
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_WIN_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_WIN_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_WIN_SOUND, false));
    }

    public void repeatGameOverSignalThreeTimesThenClose() {
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_GAME_OVER_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_GAME_OVER_SOUND, false));
        queue.add(new SignalQueueEntry(Asset.SIGNAL_GAME_OVER_SOUND, false));
    }

    private void playMorse(Asset asset) {
        morse = assetManager.get(asset.getReference(), Sound.class);
        morse.play(1.4f);
        Log.debug("Playing: "+asset.getReference());
    }
}
