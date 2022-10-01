package hu.mudlee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hu.mudlee.ui.Font;

import java.util.HashMap;
import java.util.Map;

import static hu.mudlee.Constants.SECONDARY_COLOR;

public class Styles {
	private static final Map<Font, FreeTypeFontGenerator> generatorCache = new HashMap<>();

	public static Label.LabelStyle standardText(Font font, int fontSize) {
		ensureFontGenerator(font);

		final var style = new Label.LabelStyle();
		style.font = generateFont(font, fontSize);
		style.fontColor = SECONDARY_COLOR;

		return style;
	}

	public static void dispose() {
		for (FreeTypeFontGenerator generator : generatorCache.values()) {
			generator.dispose();
		}
	}

	private static BitmapFont generateFont(Font font, int fontSize) {
		final var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.genMipMaps = false;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		return generatorCache.get(font).generateFont(parameter);
	}

	private static void ensureFontGenerator(Font font) {
		if(!generatorCache.containsKey(font)) {
			generatorCache.put(font, new FreeTypeFontGenerator(Gdx.files.internal(font.getFont())));
		}
	}
}
