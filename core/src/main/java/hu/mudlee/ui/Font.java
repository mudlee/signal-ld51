package hu.mudlee.ui;

public enum Font {
  COMMODORE("fonts/C64_Pro_Mono-STYLE.ttf");

  private final String font;

  Font(String font) {
    this.font = font;
  }

  public String getFont() {
    return font;
  }
}
