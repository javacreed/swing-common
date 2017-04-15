package com.javacreed.api.swing.common.combobox;

import java.util.Objects;

public class StringFilter extends AbstractFilter<String> {

  private final StringMatcher matcher;

  public StringFilter(final String criteria) throws NullPointerException {
    this(criteria, StringSearchUtils::indexOf);
  }

  public StringFilter(final String criteria, final StringMatcher matcher) throws NullPointerException {
    super(criteria);
    this.matcher = Objects.requireNonNull(matcher);
  }

  @Override
  public boolean accept(final String item) {
    if (value == null || item != null && -1 != matcher.indexOf(item, value, 0)) {
      return true;
    }

    return false;
  }
}
