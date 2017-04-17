package com.javacreed.api.swing.common.text;

import javax.swing.event.DocumentEvent;

@FunctionalInterface
public interface TextChangedListener {

  void changed(final DocumentEvent e);
}
