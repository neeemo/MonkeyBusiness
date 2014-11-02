package com.monkeymusicchallenge.gui;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by Daniel on 11/2/2014.
 */
public class Window extends JFrame {

  public Window(int worldNr) {

    WorldFactory factory = new WorldFactory("worlds");

    try {
      factory.createWorld(worldNr, this);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new Window(Integer.parseInt(args[0]));
  }
}
