package com.javacreed.api.swing.common.table;

import java.util.Objects;

public class Column<T> {

  public static class NotEditable<E> implements ValueSetter<E> {
    @Override
    public boolean isEditable(final E t) {
      return false;
    }

    @Override
    public void set(final Object value, final E t) {}
  };

  private final Class<?> type;
  private final String name;
  private final ValueGetter<T> valueGetter;
  private final ValueSetter<T> valueSetter;

  public Column(final Class<?> type, final String name, final ValueGetter<T> valueExtractor)
      throws NullPointerException {
    this(type, name, valueExtractor, new NotEditable<>());
  }

  public Column(final Class<?> type, final String name, final ValueGetter<T> valueExtractor,
      final ValueSetter<T> valueSetter) throws NullPointerException {
    this.type = Objects.requireNonNull(type);
    this.name = Objects.requireNonNull(name);
    this.valueGetter = Objects.requireNonNull(valueExtractor);
    this.valueSetter = Objects.requireNonNull(valueSetter);
  }

  public Object get(final T object) {
    return valueGetter.get(object);
  }

  public String getName() {
    return name;
  }

  public Class<?> getType() {
    return type;
  }

  public boolean isEditable(final T object) {
    return valueSetter.isEditable(object);
  }

  public void set(final Object value, final T object) {
    valueSetter.set(value, object);
  }

  @Override
  public String toString() {
    return name;
  }
}