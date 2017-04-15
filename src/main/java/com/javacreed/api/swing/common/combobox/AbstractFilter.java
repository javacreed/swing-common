package com.javacreed.api.swing.common.combobox;

public abstract class AbstractFilter<T> implements Filter<T> {

  protected final T value;

  public AbstractFilter(final T value) {
    this.value = value;
  }

  @Override
  public T getSelected() {
    return value;
  }
}
