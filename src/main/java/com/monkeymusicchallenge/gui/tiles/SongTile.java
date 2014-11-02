package com.monkeymusicchallenge.gui.tiles;

import com.monkeymusicchallenge.gui.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 11/2/2014.
 */
public class SongTile extends Tile {
  public SongTile() {
    super();

    try {
      BufferedImage image = ImageIO.read(new File("img/song.png"));
      Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
      add(new JLabel(new ImageIcon(scaledImage)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
