package com.javacreed.api.swing.common.table;

@FunctionalInterface
public interface ValueGetter<T> {
  Object get(T t);
}