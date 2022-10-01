package hu.mudlee;

import com.badlogic.gdx.Gdx;

import java.time.LocalDateTime;

public class Log {
  public static void debug(String message) {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    final var file = stackTraceElements.length < 3 ? "?" : stackTraceElements[2].getFileName();
    final var line = stackTraceElements.length < 3 ? -1 : stackTraceElements[2].getLineNumber();
    final var datetime = LocalDateTime.now();

    Gdx.app.debug(
      "DEBUG",
      String.format("%s [%s] %s [%d]: %s", datetime, Thread.currentThread().getName(), file, line, message)
    );
  }
}
