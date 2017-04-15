package com.javacreed.api.swing.common.combobox;

@FunctionalInterface
public interface StringMatcher {

  int indexOf(String source, String pattern, int index);
}
