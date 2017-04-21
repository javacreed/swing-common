package com.javacreed.api.swing.common.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

public class BasicTableModel<T> extends AbstractTableModel implements Iterable<T> {

  @FunctionalInterface
  public static interface RowEqualizer<E> {
    boolean areEquals(E a, E b);
  }

  private static final long serialVersionUID = 3543147549154064649L;

  private final RowEqualizer<T> rowEqualizer = this::defaultAreRowEquals;

  private final transient List<Column<T>> columns = new ArrayList<>();

  private List<T> rows = new ArrayList<>();

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

  public int addRowIfUnique(final T row) {
    for (int i = 0, size = rows.size(); i < size; i++) {
      final T existing = rows.get(i);
      if (rowEqualizer.areEquals(row, existing)) {
        return -(i - 1);
      }
    }

    return addRow(row);
  }

  public void addRows(final Collection<T> rows) {
    if (false == rows.isEmpty()) {
      final int index = this.rows.size();
      this.rows.addAll(rows);
      fireTableRowsInserted(index, rows.size() - 1);
    }
  }

  public void addRowsIfUnique(final Collection<T> rows) {
    for (final T row : rows) {
      addRowIfUnique(row);
    }
  }

  public void clear() {
    clearSilently();
    fireTableDataChanged();
  }

  public void clearSilently() {
    rows.clear();
  }

  protected boolean defaultAreRowEquals(final T a, final T b) {
    if (a == null) {
      return b == null;
    }

    return a.equals(b);
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

  public boolean isEmpty() {
    return rows.isEmpty();
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

  public void shareRows(final List<T> rows) throws NullPointerException {
    this.rows = Objects.requireNonNull(rows);
    fireTableDataChanged();
  }

  public void sort(final Comparator<T> comparator) {
    Collections.sort(rows, comparator);
    fireTableDataChanged();
  }

  public int updateRowObject(final T object) {
    final int rowIndex = findRowIndex(object);

    if (rowIndex != -1) {
      setRowObjectAt(object, rowIndex);
    }

    return rowIndex;
  }
}
