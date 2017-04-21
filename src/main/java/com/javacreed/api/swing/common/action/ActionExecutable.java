package com.javacreed.api.swing.common.action;

import java.awt.event.ActionEvent;

@FunctionalInterface
public interface ActionExecutable {
  void execute(ActionEvent event);
}