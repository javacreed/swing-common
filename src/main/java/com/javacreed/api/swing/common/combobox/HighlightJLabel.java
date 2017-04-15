package com.javacreed.api.swing.common.combobox;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.UIManager;

public class HighlightJLabel extends JLabel {

  private static final long serialVersionUID = 4496132379083693526L;

  private boolean applyHighlight;

  private String highlightText;

  private Color highlightColour = UIManager.getColor("List.selectionBackground");

  private StringMatcher matcher = StringSearchUtils::indexOf;

  public Color getHighlightColour() {
    return highlightColour;
  }

  public String getHighlightText() {
    return highlightText;
  }

  public StringMatcher getMatcher() {
    return matcher;
  }

  public boolean hasHighlightText() {
    return highlightText != null && highlightText.isEmpty() == false;
  }

  public boolean isApplyHighlight() {
    return applyHighlight;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    if (isApplyHighlight() && hasHighlightText()) {
      final Graphics gg = g.create();
      final FontMetrics metrics = gg.getFontMetrics(getFont());

      final String text = getText();
      for (int i = -1; (i = matcher.indexOf(text, highlightText, i + 1)) != -1;) {
        final String start = text.substring(0, i);

        final int startX = metrics.stringWidth(start);
        final int startY = 0;
        final int length = metrics.stringWidth(highlightText);
        final int height = metrics.getHeight();

        gg.setColor(highlightColour);
        gg.fillRect(startX, startY, length, height);
      }
    }

    super.paintComponent(g);
  }

  public void setApplyHighlight(final boolean applyHighlight) {
    this.applyHighlight = applyHighlight;
  }

  public void setHighlightColour(final Color highlightColour) {
    this.highlightColour = Objects.requireNonNull(highlightColour);
    if (hasHighlightText()) {
      repaint();
    }
  }

  public void setHighlightText(final String highlight) {
    this.highlightText = highlight;
    repaint();
  }

  public void setMatcher(final StringMatcher matcher) {
    this.matcher = matcher;
  }
}