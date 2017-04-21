package com.javacreed.api.swing.common.table;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

public class BasicRowFilter<T extends TableModel> extends RowFilter<T, Object> {

  public static String removeDiacriticalMarks(final String string) {
    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

  private final List<String> filterCriteria;

  public BasicRowFilter(final String filterCriteria) {
    this.filterCriteria = new ArrayList<String>();
    final Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(filterCriteria);
    while (matcher.find()) {
      this.filterCriteria.add(matcher.group(1).replace("\"", ""));
    }
  }

  @Override
  public boolean include(final RowFilter.Entry<? extends T, ? extends Object> entry) {
    for (final String criterion : filterCriteria) {
      boolean found = false;

      for (int i = 0, size = entry.getValueCount(); i < size; i++) {
        final String value = entry.getStringValue(i);
        final String lowerValue = value.toLowerCase();
        if (criterion.equalsIgnoreCase(value) || lowerValue.contains(criterion)
            || BasicRowFilter.removeDiacriticalMarks(lowerValue).contains(criterion)) {
          found = true;
        }
      }

      if (false == found) {
        return false;
      }
    }

    return true;
  }
}