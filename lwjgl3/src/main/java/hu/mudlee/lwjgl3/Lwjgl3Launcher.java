package hu.mudlee.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import hu.mudlee.LD51Game;

public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new LD51Game(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("LD51");
		cfg.useVsync(false);
		cfg.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		cfg.setWindowedMode(
				Lwjgl3ApplicationConfiguration.getDisplayMode().width / 2,
				Lwjgl3ApplicationConfiguration.getDisplayMode().height / 2
		);
		cfg.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return cfg;
	}
}