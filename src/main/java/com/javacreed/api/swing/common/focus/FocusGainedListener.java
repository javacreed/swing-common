package com.javacreed.api.swing.common.focus;

import java.awt.event.FocusEvent;

@FunctionalInterface
public interface FocusGainedListener {
  void focusGained(FocusEvent e);
}
