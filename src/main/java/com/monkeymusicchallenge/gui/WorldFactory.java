package com.monkeymusicchallenge.gui;

import com.monkeymusicchallenge.gui.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Daniel on 11/2/2014.
 */
public class WorldFactory {

  private final File worldFile;

  public WorldFactory(String worlds) {
    worldFile = new File(worlds);
  }

  public void createWorld(int worldNr, JFrame window) throws FileNotFoundException {
    String world = extractWorld(worldNr);
    Scanner worldScanner = new Scanner(world);

    List<Tile> tiles = new ArrayList<>();

    int gridY = 0;
    int gridX = 0;
    while (worldScanner.hasNextLine()) {
      gridY++;
      gridX = 0;

      String line = worldScanner.nextLine();
      Scanner lineScanner = new Scanner(line);

      while(lineScanner.hasNext()) {
        gridX++;
        String tile = lineScanner.next();
        System.out.println(tile);
        switch (tile) {
          case "m":
            tiles.add(new MonkeyTile());
            break;
          case "g":
            tiles.add(new GrassTile());
            break;
          case "p":
            tiles.add(new PlaylistTile());
            break;
          case "s":
            tiles.add(new SongTile());
            break;
          case "w":
            tiles.add(new WallTile());
            break;
          case "u":
            tiles.add(new UserTile());
            break;
          case "a":
            tiles.add(new AlbumTile());
            break;
        }
      }
    }

    GridLayout grid = new GridLayout(gridX, gridY);
    window.setLayout(grid);

    for (Tile tile: tiles) {
      window.add(tile);
    }
  }

  private String extractWorld(int worldNr) throws FileNotFoundException {
    Scanner scanner = new Scanner(worldFile);
    scanner.useDelimiter("(#.*)");
    int skippedWorlds = 0;

    while (scanner.hasNext() && worldNr > skippedWorlds) {
      scanner.next();
      skippedWorlds++;
    }

    return scanner.next().trim();
  }
}
