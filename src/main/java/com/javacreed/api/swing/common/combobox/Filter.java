package com.javacreed.api.swing.common.combobox;

public interface Filter<T> {

  boolean accept(T item);

  T getSelected();

}