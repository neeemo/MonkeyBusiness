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
public class WallTile extends Tile {
  public WallTile() {
    super();

    try {
      BufferedImage sprites = ImageIO.read(new File("img/overworld.png"));
      BufferedImage sprite = sprites.getSubimage(112, 64, 16, 16);
      Image image = sprite.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

      add(new JLabel(new ImageIcon(image)));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
