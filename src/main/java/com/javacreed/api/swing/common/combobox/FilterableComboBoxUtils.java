package com.javacreed.api.swing.common.combobox;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import com.javacreed.api.swing.common.utils.SwingUtils;

public class FilterableComboBoxUtils {

  @FunctionalInterface
  public static interface FilterFactory<T> {
    Filter<T> create(String value);
  }

  public static JComboBox<String> create(final FilterableComboBoxModel<String> model) {
    return FilterableComboBoxUtils.create(model, (c) -> new StringFilter(c));
  }

  public static <T> JComboBox<T> create(final FilterableComboBoxModel<T> model, final FilterFactory<T> factory) {
    final HighlightListCellRenderer<T> renderer = new HighlightListCellRenderer<>();

    final JComboBox<T> comboBox = new JComboBox<>(model);
    comboBox.setRenderer(renderer);
    comboBox.setEditable(true);

    final JTextComponent editer = (JTextComponent) comboBox.getEditor().getEditorComponent();
    SwingUtils.addSelectAllOnFocusGain(editer);
    editer.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(final DocumentEvent e) {
        update(e);
      }

      @Override
      public void insertUpdate(final DocumentEvent e) {
        update(e);
      }

      @Override
      public void removeUpdate(final DocumentEvent e) {
        update(e);
      }

      private void update(final DocumentEvent e) {
        SwingUtilities.invokeLater(() -> {
          comboBox.hidePopup();
          renderer.setHighlightText(editer.getText());
          model.filter(factory.create(editer.getText()));
          if (comboBox.isVisible()) {
            comboBox.showPopup();
            comboBox.addPopupMenuListener(new PopupMenuListener() {
              @Override
              public void popupMenuCanceled(final PopupMenuEvent e) {}

              @Override
              public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
                final int index = comboBox.getSelectedIndex();
                if (index >= 0 && index < model.getSize()) {
                  final T selected = model.getElementAt(index);
                  SwingUtilities.invokeLater(() -> {
                    /* TODO cater for non-string values */
                    editer.setText((String) selected);
                    comboBox.hidePopup();
                  });
                }
              }

              @Override
              public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {}
            });
          }
        });
      }
    });

    return comboBox;
  }

  public static JComboBox<String> create(final List<String> elements) {
    return FilterableComboBoxUtils.create(new FilterableComboBoxModel<>(elements));
  }

  public static JComboBox<String> setText(final JComboBox<String> comboBox, final String text) {
    comboBox.setSelectedItem(text);
    final Component editor = comboBox.getEditor().getEditorComponent();
    if (editor instanceof JTextComponent) {
      ((JTextComponent) editor).setText(text);
    }
    return comboBox;
  }
}
