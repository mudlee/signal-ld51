package hu.mudlee;

import com.badlogic.gdx.audio.Sound;

public enum Asset {
  //AUDIO_AMBIENT("ambient_game.ogg", Music.class),
  SIGNAL_INITIAL_SOUND("audio/signal_initial.ogg", Sound.class),
  SIGNAL_INVALID_SOUND("audio/signal_invalid_answer.ogg", Sound.class),
  SIGNAL_WIN_SOUND("audio/signal_win.ogg", Sound.class),
  SIGNAL_GAME_OVER_SOUND("audio/signal_game_over.ogg", Sound.class),
//  BIG_HOME_ATLAS("big_home.png", Texture.class),
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
