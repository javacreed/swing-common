package com.javacreed.api.swing.common.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class DocumentAdapter implements DocumentListener {

  public static <T extends JTextComponent> T wrap(final T textComponent, final TextChangedListener listener) {
    textComponent.getDocument().addDocumentListener(new TextChangedAdapter(listener));
    return textComponent;
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    updated(e);
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    updated(e);
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    updated(e);
  }

  public void updated(final DocumentEvent e) {}

}
