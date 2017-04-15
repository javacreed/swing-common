package com.javacreed.api.swing.common.combobox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

public class FilterableComboBoxModel<T> extends AbstractListModel<T> implements MutableComboBoxModel<T> {

  private static final long serialVersionUID = -8587258615897622266L;

  private final List<T> list = new ArrayList<>();

  private final List<T> filtered = new ArrayList<>();

  private T selected;

  private final Filter<T> defaultFilter = new Filter<T>() {
    @Override
    public boolean accept(final T item) {
      return true;
    }

    @Override
    public T getSelected() {
      return selected;
    }
  };

  private Filter<T> filter = defaultFilter;

  public FilterableComboBoxModel() {}

  public FilterableComboBoxModel(final Iterable<T> elements) {
    addElements(elements);
  }

  @Override
  public void addElement(final T element) {
    list.add(element);
    filter();
  }

  public void addElements(final Iterable<T> elements) {
    for (final Iterator<T> i = elements.iterator(); i.hasNext();) {
      list.add(i.next());
    }
    filter();
  }

  public void addElements(final T[] elements) {
    list.addAll(Arrays.asList(elements));
    filter();
  }

  public void filter() {
    filtered.clear();
    for (final T item : list) {
      if (filter.accept(item)) {
        filtered.add(item);
      }
    }
    this.selected = filter.getSelected();
    fireContentsChanged(this, 0, filtered.size());
  }

  public void filter(final Filter<T> filter) {
    this.filter = filter == null ? defaultFilter : filter;
    filter();
  }

  @Override
  public T getElementAt(final int index) {
    return filtered.get(index);
  }

  @Override
  public Object getSelectedItem() {
    return selected;
  }

  @Override
  public int getSize() {
    return filtered.size();
  }

  @Override
  public void insertElementAt(final T element, final int index) {
    list.add(index, element);
    fireIntervalAdded(this, index, index);
  }

  @Override
  public void removeElement(final Object object) {
    int min = 0;
    int max = 0;
    for (int i = list.size() - 1; i >= 0; i--) {
      if (object.equals(list.get(i))) {
        list.remove(i);
        min = Math.min(min, i);
        max = Math.max(max, i);
      }
    }

    fireIntervalRemoved(this, min, max);
  }

  @Override
  public void removeElementAt(final int index) {
    list.remove(index);
    fireIntervalRemoved(this, index, index);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setSelectedItem(final Object selected) {
    this.selected = (T) selected;
  }
}