package com.monkeymusicchallenge.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * The rough outline for any game object.
 *
 * Created by Daniel on 11/2/2014.
 */
public abstract class Tile extends JPanel {
  // Any tile can have a single tag which can help
  // identify specific tiles.
  private String tag = "";

  public Tile() {
    setBackground(new Color(153, 230, 57));
    setBorder(new LineBorder(Color.BLACK));
    setSize(100, 100);
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public boolean hasTag(String tag) {
    return this.tag.equals(tag);
  }

  /**
   * Tiles should respond with a short type name such as "monkey" or "empty".
   * @return A short, lowercase tile-type name.
   */
  @Override
  public abstract String toString();
}
