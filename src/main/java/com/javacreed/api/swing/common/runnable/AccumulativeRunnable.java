package com.javacreed.api.swing.common.runnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

public abstract class AccumulativeRunnable<T> implements Runnable {

  private List<T> arguments = null;

  @SafeVarargs
  public final synchronized void add(final T... args) {
    boolean isSubmitted = true;
    if (arguments == null) {
      isSubmitted = false;
      arguments = new ArrayList<T>();
    }

    Collections.addAll(arguments, args);
    if (!isSubmitted) {
      submit();
    }
  }

  private final synchronized List<T> flush() {
    final List<T> list = arguments;
    arguments = null;
    return list;
  }

  @Override
  public void run() {
    run(flush());
  }

  protected abstract void run(List<T> args);

  protected void submit() {
    SwingUtilities.invokeLater(this);
  }

}
