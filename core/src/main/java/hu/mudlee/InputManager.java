package hu.mudlee;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputManager {
    private final InputMultiplexer multiplexer = new InputMultiplexer();

    public InputProcessor getMultiplexer() {
        return multiplexer;
    }

    public void addInputProcessor(NamedInputProcessor processor) {
        Log.debug("Adding input processor: "+processor.getName());
        multiplexer.addProcessor(processor);
    }

    public void addInputProcessor(InputProcessor processor, String name) {
        Log.debug("Adding input processor: "+name);
        multiplexer.addProcessor(processor);
    }

    public void clearInputProcessors() {
        Log.debug("Clearing input processors");
        multiplexer.clear();
    }
}
