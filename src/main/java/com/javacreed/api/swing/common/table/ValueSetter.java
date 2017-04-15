package com.javacreed.api.swing.common.table;

@FunctionalInterface
public interface ValueSetter<T> {

  default boolean isEditable(final T object) {
    return true;
  }

  void set(Object value, T object);
}
