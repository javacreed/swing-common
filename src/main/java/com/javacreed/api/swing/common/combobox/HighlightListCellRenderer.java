package com.javacreed.api.swing.common.combobox;

import java.awt.Component;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class HighlightListCellRenderer<T> extends HighlightJLabel implements ListCellRenderer<T> {

  @FunctionalInterface
  public static interface ValueToStringConverter<T> {
    String convert(T t);
  }

  private static final long serialVersionUID = -1251621072561436202L;

  private static String convertString(final Object object) {
    return object == null ? "" : object.toString();
  }

  private final ValueToStringConverter<T> converter;

  public HighlightListCellRenderer() throws NullPointerException {
    this(HighlightListCellRenderer::convertString);
  }

  public HighlightListCellRenderer(final ValueToStringConverter<T> converter) throws NullPointerException {
    this.converter = Objects.requireNonNull(converter);
  }

  @Override
  public Component getListCellRendererComponent(final JList<? extends T> list, final T value, final int index,
      final boolean isSelected, final boolean cellHasFocus) {

    if (isSelected) {
      setApplyHighlight(false);
      setOpaque(true);
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setApplyHighlight(true);
      setOpaque(false);
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    setText(converter.convert(value));

    return this;
  }
}