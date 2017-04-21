package com.javacreed.api.swing.common.action;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.javacreed.api.swing.common.utils.SwingUtils;

public class ButtonAction extends AbstractAction {

  private static final long serialVersionUID = -2968272641905826110L;

  private final ActionExecutable executable;

  public ButtonAction(final String name, final ActionExecutable executable) {
    putValue(Action.NAME, name);
    putValue(Action.ACTION_COMMAND_KEY, name);
    putValue(Action.SHORT_DESCRIPTION, name);
    this.executable = Objects.requireNonNull(executable);
  }

  public ButtonAction(final String name, final String iconName, final ActionExecutable executable) {
    putValue(Action.NAME, name);
    putValue(Action.ACTION_COMMAND_KEY, name);
    putValue(Action.SHORT_DESCRIPTION, name);
    putValue(Action.LARGE_ICON_KEY, SwingUtils.getIcon("24/" + iconName + ".png"));
    this.executable = Objects.requireNonNull(executable);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    executable.execute(e);
  }

  public ButtonAction toolTip(final String toolTip) {
    putValue(Action.SHORT_DESCRIPTION, toolTip);
    return this;
  }
}