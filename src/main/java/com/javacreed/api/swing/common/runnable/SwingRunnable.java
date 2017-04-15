package com.javacreed.api.swing.common.runnable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

public abstract class SwingRunnable<T> implements Runnable {

  public static void invokeAndWaitQuietly(final Runnable runnable) {
    try {
      SwingUtilities.invokeAndWait(runnable);
    } catch (final InvocationTargetException e) {
      throw new RuntimeException("Failed to wait for event thread to finish", e);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while waiting for event thread to finish", e);
    }
  }

  public static void waitFor() {
    SwingRunnable.invokeAndWaitQuietly(() -> {});
  }

  private AccumulativeRunnable<Object> doProcessUpdates;

  protected void done() {}

  protected abstract T executeInBackground() throws Exception;

  protected void failed(final Throwable e) {}

  protected void processUpdates(final List<Object> objects) {}

  @Override
  public void run() {
    try {
      final T t = executeInBackground();
      SwingUtilities.invokeLater(() -> succeeded(t));
    } catch (final Throwable e) {
      SwingUtilities.invokeLater(() -> failed(e));
    } finally {
      SwingUtilities.invokeLater(() -> done());
    }
  }

  public final void sendUpdates(final Object... objects) {
    synchronized (this) {
      if (doProcessUpdates == null) {
        doProcessUpdates = new AccumulativeRunnable<Object>() {
          @Override
          public void run(final List<Object> objects) {
            processUpdates(objects);
          }
        };
      }
    }
    doProcessUpdates.add(objects);
  }

  protected void succeeded(final T t) {}

}
