package com.javacreed.api.swing.common.utils;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import com.javacreed.api.swing.common.focus.FocusGainedListener;
import com.javacreed.api.swing.common.focus.FocusLostListener;
import com.javacreed.api.swing.common.text.DocumentValidationListener;
import com.javacreed.api.swing.common.text.TextFieldValidator;

public class SwingUtils {

  @FunctionalInterface
  public static interface TextValidation {
    boolean isValid(String text);
  }

  public static final Color ERROR_BACKGROUND_COLOUR = new Color(255, 220, 220);

  public static void addSelectAllOnFocusGain(final JTextComponent textComponent) {
    textComponent.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(final FocusEvent e) {
        textComponent.selectAll();
      }
    });
  }

  public static void addTransferFocusOnEnter(final Container container) {
    final Set<AWTKeyStroke> forwardKeys = container.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
    final Set<AWTKeyStroke> newForwardKeys = new HashSet<>(forwardKeys);
    newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
    container.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);
  }

  /**
   * Had an issue with the radio buttons and had to write this workaround until I sort out this problem
   *
   * @param component
   * @param target
   */
  public static void addTransferFocusOnEnter(final JComponent component, final JComponent target) {
    component.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          target.grabFocus();
        }
      };
    });
  }

  public static void addTransferFocusOnEnter(final Window window) {
    final Set<AWTKeyStroke> forwardKeys = window.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
    final Set<AWTKeyStroke> newForwardKeys = new HashSet<>(forwardKeys);
    newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
    window.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);
  }

  public static TextFieldValidator addValidator(final JTextField textField, final TextValidation validation) {
    final TextFieldValidator validator = (t) -> {
      // if (t.isEditable() == false || validation.isValid(t.getText())) {
      if (validation.isValid(t.getText())) {
        if (textField.isEditable()) {
          t.setBackground(UIManager.getColor("TextField.background"));
        } else {
          t.setBackground(UIManager.getColor("TextField.inactiveBackground"));
        }
      } else {
        final Color color = UIManager.getColor("TextField.errorBackground");
        t.setBackground(color == null ? SwingUtils.ERROR_BACKGROUND_COLOUR : color);
      }
    };
    textField.addPropertyChangeListener("editable", (e) -> validator.validate(textField));
    validator.validate(textField);
    DocumentValidationListener.create(textField, validator);
    return validator;
  }

  public static JButton createButton(final Action action) {
    final JButton button = new JButton(action);
    button.setMargin(new Insets(1, 5, 1, 5));
    button.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
    button.setToolTipText((String) action.getValue(Action.ACTION_COMMAND_KEY));
    return button;
  }

  public static FocusListener createFocusGainedListener(final FocusGainedListener listener) {
    return new FocusAdapter() {
      @Override
      public void focusGained(final FocusEvent e) {
        listener.focusGained(e);
      }
    };
  }

  public static FocusListener createFocusLostListener(final FocusLostListener listener) {
    return new FocusAdapter() {
      @Override
      public void focusLost(final FocusEvent e) {
        listener.focusLost(e);
      }
    };
  }

  public static JButton createIconButton(final Action action) {
    final JButton button = new JButton(action);
    button.setText("");
    button.setMargin(new Insets(1, 5, 1, 5));
    button.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
    button.setToolTipText((String) action.getValue(Action.ACTION_COMMAND_KEY));
    return button;
  }

  public static JToggleButton createToggelButton(final Action action) {
    final JToggleButton button = new JToggleButton(action);
    button.setMargin(new Insets(1, 5, 1, 5));
    button.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
    return button;
  }

  public static Icon getIcon(final String name) {
    final Image image = SwingUtils.getImage(name);
    if (image == null) {
      return new ImageIcon();
    }

    return new ImageIcon(image);
  }

  public static Image getImage(final String name) {
    try (InputStream is = SwingUtils.class.getResourceAsStream("/icons/" + name)) {
      if (is != null) {
        return ImageIO.read(is);
      }
    } catch (final Exception e) {}

    return null;
  }

  public static void registerKeyAction(final JComponent component, final KeyStroke keyStroke, final Action action) {
    component.getInputMap().put(keyStroke, action.getValue(Action.ACTION_COMMAND_KEY));
    component.getActionMap().put(action.getValue(Action.ACTION_COMMAND_KEY), action);
  }

  public static void registerKeyAction(final JComponent component, final String key, final Action action) {
    SwingUtils.registerKeyAction(component, KeyStroke.getKeyStroke(key), action);
  }

  public static void scrollToSelectedRow(final JTable table) {
    final int row = Math.max(0, table.getSelectedRow());
    if (table.getRowCount() > row) {
      final Rectangle rect = table.getCellRect(row, 0, true);
      table.scrollRectToVisible(rect);
    }
  }

  private SwingUtils() {}
}
