package hu.mudlee;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public enum Asset {
  AUDIO_AMBIENT("audio/ambient.ogg", Music.class),
  SIGNAL_INITIAL_SOUND("audio/signal_initial.ogg", Music.class),
  SIGNAL_INVALID_SOUND("audio/signal_invalid_answer.ogg", Music.class),
  SIGNAL_WIN_SOUND("audio/signal_win.ogg", Music.class),
  SIGNAL_GAME_OVER_SOUND("audio/signal_game_over.ogg", Music.class),
  KEY_1("audio/key1.ogg", Sound.class),
  KEY_2("audio/key2.ogg", Sound.class),
  KEY_3("audio/key3.ogg", Sound.class),
  KEY_4("audio/key4.ogg", Sound.class),
  A_KEY_1("audio/a_key1.ogg", Sound.class),
  A_KEY_2("audio/a_key2.ogg", Sound.class),
  A_KEY_3("audio/a_key3.ogg", Sound.class),
  A_KEY_4("audio/a_key4.ogg", Sound.class),
  ;

  private final String reference;
  private final Class<?> type;

  Asset(String reference, Class<?> type) {
    this.reference = reference;
    this.type = type;
  }

  public String getReference() {
    return reference;
  }

  public Class<?> getType() {
    return type;
  }
}
