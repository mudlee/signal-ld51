package hu.mudlee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static hu.mudlee.Constants.SIGNAL_REPEAT_FREQUENCY_MILLIS;

public class SignalPlayer {
    private record SignalQueueEntry(Asset morse, boolean loop, float volume){}

    private final AssetManager assetManager;
    private final Queue<SignalQueueEntry> queue = new LinkedBlockingQueue<>();
    private double lastPlayed = 0;
    private Music signal;
    private boolean gameover = false;
    private boolean won = false;

    public SignalPlayer(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public boolean isPlaying() {
        return signal.isPlaying();
    }

    public void tick() {
        if(gameover && !signal.isPlaying()) {
            Gdx.app.exit();
        }

        if(queue.isEmpty()) {
            return;
        }

        double now = System.currentTimeMillis();
        if ((lastPlayed + SIGNAL_REPEAT_FREQUENCY_MILLIS) < now || lastPlayed == 0 || won) {
            var next = queue.poll();
            lastPlayed = now;
            playMorse(next.morse, next.volume);
            if (next.loop) {
                var old = queue.stream().toList();
                queue.clear();
                queue.add(next);
                queue.addAll(old);
            }
        }
    }

    public void repeatInitialSequence() {
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INITIAL_SOUND, true, 0.4f));
    }

    public void playInvalidAnswerSignalThen(Asset asset) {
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_INVALID_SOUND, false, 0.4f));
        queue.add(new SignalQueueEntry(asset, true, 0.4f));
    }

    public void planWinSignalThenClose() {
        won = true;
        signal.stop();
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_WIN_SOUND, false, 1f));
    }

    public void playGameOverSignalThenClose() {
        queue.clear();
        queue.add(new SignalQueueEntry(Asset.SIGNAL_GAME_OVER_SOUND, false, 1f));
    }

    private void playMorse(Asset asset, float volume) {
        signal = assetManager.get(asset.getReference(), Music.class);
        signal.play();
        signal.setVolume(volume);
        if (asset == Asset.SIGNAL_GAME_OVER_SOUND) {
            gameover = true;
        }
        Log.debug("Playing: "+asset.getReference()+", volume: "+volume);
    }
}
