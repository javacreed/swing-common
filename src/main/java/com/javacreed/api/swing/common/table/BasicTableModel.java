package com.javacreed.api.swing.common.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BasicTableModel<T> extends AbstractTableModel implements Iterable<T> {

  private static final long serialVersionUID = 6069717250611750536L;

  private final transient List<Column<T>> columns = new ArrayList<>();

  private final List<T> rows = new ArrayList<>();

  public void addColumn(final Column<T> column) {
    this.columns.add(column);
    fireTableStructureChanged();
  }

  public int addRow(final T row) {
    final int index = this.rows.size();
    this.rows.add(row);
    fireTableRowsInserted(index, index);
    return index;
  }

  public void clear() {
    clearSilently();
    fireTableDataChanged();
  }

  public void clearSilently() {
    rows.clear();
  }

  public int findRowIndex(final T object) {
    for (int i = 0, size = rows.size(); i < size; i++) {
      if (object.equals(rows.get(i))) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return columns.get(columnIndex).getType();
  }

  @Override
  public int getColumnCount() {
    return columns.size();
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return columns.get(columnIndex).getName();
  }

  public T getRowAt(final int index) {
    return rows.get(index);
  }

  @Override
  public int getRowCount() {
    return rows.size();
  }

  public List<T> getRows() {
    return rows;
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    final T row = rows.get(rowIndex);
    return columns.get(columnIndex).get(row);
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    final T row = rows.get(rowIndex);
    return columns.get(columnIndex).isEditable(row);
  }

  @Override
  public Iterator<T> iterator() {
    return rows.iterator();
  }

  public void removeRowAt(final int rowIndex) {
    rows.remove(rowIndex);
    fireTableRowsDeleted(rowIndex, rowIndex);
  }

  public boolean removeRowObject(final T object) {
    final int rowIndex = findRowIndex(object);
    if (rowIndex == -1) {
      return false;
    }

    removeRowAt(rowIndex);
    return true;
  }

  public boolean removeRowObjects(final Collection<T> objects) {
    boolean removed = false;
    for (final T object : objects) {
      if (removeRowObject(object)) {
        removed = true;
      }
    }
    return removed;
  }

  public void setRowObjectAt(final T object, final int rowIndex) {
    rows.set(rowIndex, object);
    fireTableRowsUpdated(rowIndex, rowIndex);
  }

  public void setRows(final Collection<T> rows) {
    this.rows.clear();
    this.rows.addAll(rows);
    fireTableDataChanged();
  }

  @Override
  public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
    final T row = rows.get(rowIndex);
    columns.get(columnIndex).set(value, row);
    fireTableCellUpdated(rowIndex, columnIndex);
  }

  public int updateRowObject(final T object) {
    final int rowIndex = findRowIndex(object);

    if (rowIndex != -1) {
      setRowObjectAt(object, rowIndex);
    }

    return rowIndex;
  }
}
