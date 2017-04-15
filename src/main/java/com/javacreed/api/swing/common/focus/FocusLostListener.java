package com.javacreed.api.swing.common.focus;

import java.awt.event.FocusEvent;

@FunctionalInterface
public interface FocusLostListener {
  void focusLost(final FocusEvent e);
}
