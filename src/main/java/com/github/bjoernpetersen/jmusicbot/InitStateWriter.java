package com.github.bjoernpetersen.jmusicbot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface InitStateWriter extends Loggable {

  @Nonnull
  InitStateWriter NO_OP = new InitStateWriter() {
    @Override
    public void begin(@Nonnull Plugin plugin) {
    }

    @Override
    public void state(@Nonnull String state) {
    }

    @Override
    public void warning(@Nonnull String warning) {
    }
  };

  @Nonnull
  InitStateWriter LOG = new InitStateWriter() {
    @Nullable
    private String activePlugin;

    @Override
    public void begin(@Nonnull Plugin plugin) {
      this.activePlugin = plugin.getReadableName();
    }

    private String prepend(String prepended) {
      String pluginName = activePlugin == null ? "Unknown plugin" : activePlugin;
      return pluginName + ": " + prepended;
    }

    @Override
    public void state(@Nonnull String state) {
      logInfo(prepend(state));
    }

    @Override
    public void warning(@Nonnull String warning) {
      logWarning(prepend(warning));
    }
  };

  void begin(@Nonnull Plugin plugin);

  void state(@Nonnull String state);

  void warning(@Nonnull String warning);

  default void close() {
  }
}
