package com.javacreed.api.swing.common.table;

import java.util.Collection;

public abstract class DataEntryTableModel<T> extends BasicTableModel<T> {

  private static final long serialVersionUID = -7991694536616773653L;

  public DataEntryTableModel() {
    addRow(newBlankInstance());
  }

  public abstract T newBlankInstance();

  @Override
  public void setRows(final Collection<T> rows) {
    super.setRows(rows);
    addRow(newBlankInstance());
  }

  @Override
  public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
    super.setValueAt(value, rowIndex, columnIndex);

    if (rowIndex == getRowCount() - 1) {
      addRow(newBlankInstance());
      fireTableRowsInserted(rowIndex, rowIndex);
    }
  }
}
