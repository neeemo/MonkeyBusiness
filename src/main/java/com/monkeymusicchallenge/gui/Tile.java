package com.monkeymusicchallenge.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Daniel on 11/2/2014.
 */
public abstract class Tile extends JPanel {
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

  @Override
  public abstract String toString();
}
