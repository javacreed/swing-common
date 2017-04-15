package com.javacreed.api.swing.common.utils;

import javax.swing.JTextField;

@FunctionalInterface
public interface TextFieldValidator {

  void validate(JTextField component);
}
