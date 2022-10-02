package hu.mudlee.lwjgl3;

import com.badlogic.gdx.Graphics;
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
		cfg.setTitle("Signal for LD51 ....- --... ....- -.-. ....- ---.. ....- -....");
		cfg.useVsync(false);
		cfg.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);

		var width = Lwjgl3ApplicationConfiguration.getDisplayMode().width / 2;
		var height = Lwjgl3ApplicationConfiguration.getDisplayMode().height / 2;

		if (width < 1200) {
			cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		}

		cfg.setWindowedMode(width, height);
		cfg.setWindowIcon("icons/128.png", "icons/64.png", "icons/32.png", "icons/16.png");
		return cfg;
	}
}