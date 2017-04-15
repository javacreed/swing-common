package com.javacreed.api.swing.common.focus;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

/**
 * This is not working well
 */
public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {

  private final List<Component> components = new ArrayList<>();

  public <T extends Component> T add(final T component) {
    this.components.add(component);
    return component;
  }

  public int findComponent(final Component component) {
    for (int i = 0, size = components.size(); i < size; i++) {
      final Component existing = components.get(i);
      if (existing == component) {
        return i;
      }

      /* TODO: I need to fix this */
      if (existing instanceof JComboBox<?> && ((JComboBox<?>) existing).getEditor() == component) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public Component getComponentAfter(final Container container, final Component component) {
    final int componentIndex = findComponent(component);
    if (componentIndex == -1) {
      System.err.println("Component not found " + component);
      for (int i = 0, size = components.size(); i < size; i++) {
        final Component existing = components.get(i);
        System.err.println("  >> [" + i + "] " + existing + " (" + (component == existing ? "same" : "not same") + ")");
      }
      return components.get(0);
    }

    final int size = components.size();
    final int nextIndex = (componentIndex + 1) % size;
    for (int i = 0, index = nextIndex; i < size; i++, index = index + 1 % size) {
      final Component after = components.get(index);
      if (after.isEnabled() && after.isVisible()) {
        return after;
      }
    }

    return components.get(nextIndex);
  }

  @Override
  public Component getComponentBefore(final Container container, final Component component) {
    int index = components.indexOf(component) - 1;
    if (index < 0) {
      index = components.size() - 1;
    }
    Component before = components.get(index);
    while (index >= 0 && !(before.isEnabled() && before.isVisible())) {
      index--;
      before = components.get(index);
    }
    return before;
  }

  @Override
  public Component getDefaultComponent(final Container container) {
    return getFirstComponent(container);
  }

  @Override
  public Component getFirstComponent(final Container container) {
    int index = 0;
    Component first = components.get(index);
    while (index < components.size() && !(first.isEnabled() && first.isVisible())) {
      index++;
      first = components.get(index);
    }
    return first;
  }

  @Override
  public Component getLastComponent(final Container container) {
    int index = components.size() - 1;
    Component last = components.get(index);
    while (index >= 0 && !(last.isEnabled() && last.isVisible())) {
      index--;
      last = components.get(index);
    }
    return last;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    formatted.append("There are ").append(components.size()).append(" components");
    for (final Component component : components) {
      formatted.append("\n  >> " + component);
    }
    return formatted.toString();
  }
}
