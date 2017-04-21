package com.javacreed.api.swing.common.text;

import javax.swing.JTextField;

@FunctionalInterface
public interface TextFieldValidator {

  void validate(JTextField component);
}
