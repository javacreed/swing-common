package com.javacreed.api.swing.common.utils;

import java.util.Objects;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentValidationListener implements DocumentListener {

  public static DocumentValidationListener create(final JTextField component, final TextFieldValidator validator)
      throws NullPointerException {
    final DocumentValidationListener listener = new DocumentValidationListener(component, validator);
    component.getDocument().addDocumentListener(listener);
    return listener;
  }

  private final TextFieldValidator validator;

  private final JTextField component;

  public DocumentValidationListener(final JTextField component, final TextFieldValidator validator)
      throws NullPointerException {
    this.validator = Objects.requireNonNull(validator);
    this.component = Objects.requireNonNull(component);
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    validator.validate(component);
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    validator.validate(component);
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    validator.validate(component);
  }
}
