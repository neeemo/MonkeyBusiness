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

  public Tile[][] createWorld(int worldNr) throws FileNotFoundException {
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

      int nrOfUsers = 0;

      while(lineScanner.hasNext()) {
        gridX++;
        String tile = lineScanner.next();
        switch (tile) {
          case "m":
            Tile player = new MonkeyTile();
            player.setTag("m" + nrOfUsers++);
            tiles.add(player);
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

    Tile[][] tileGrid = new Tile[gridY][gridX];

    for (int y = 0; y< tileGrid.length; y++) {
      for (int x = 0; x < tileGrid[y].length; x++) {
        tileGrid[y][x] = tiles.get(y*gridX+x);
      }
    }

    return tileGrid;
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
