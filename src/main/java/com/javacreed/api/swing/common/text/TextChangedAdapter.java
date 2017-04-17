package com.javacreed.api.swing.common.text;

import java.util.Objects;

import javax.swing.event.DocumentEvent;

public class TextChangedAdapter extends DocumentAdapter {

  private final TextChangedListener listener;

  public TextChangedAdapter(final TextChangedListener listener) throws NullPointerException {
    this.listener = Objects.requireNonNull(listener);
  }

  @Override
  public void updated(final DocumentEvent e) {
    listener.changed(e);
  }
}