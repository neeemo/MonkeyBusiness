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
 * The factory parses a world from a textual specification and produces
 * a 2-dimensional array of appropriate tiles.
 *
 * Created by Daniel on 11/2/2014.
 */
public class WorldFactory {
  private final File worldFile;

  /**
   * @param worlds The location of the file containg the world specifications.
   */
  public WorldFactory(String worlds) {
    worldFile = new File(worlds);
  }

  /**
   * Creates a tile grid according to the n'th world in the world specification file.
   *
   * @param worldNr The desired world index. 0-indexed.
   * @return a tile grid corresponding to the specification.
   * @throws FileNotFoundException
   */
  public Tile[][] createWorld(int worldNr) throws FileNotFoundException {
    // Disregard other worlds in specification file.
    String world = extractWorld(worldNr);
    Scanner worldScanner = new Scanner(world);

    // We'll store the tiles in a list first, as we don't know
    // how many tiles there will be.
    List<Tile> tiles = new ArrayList<>();

    // variables for recording the number of columns and rows in the
    // specification.
    int rows = 0;
    int columns = 0;

    // parse the world row by row
    while (worldScanner.hasNextLine()) {
      rows++;
      // Reset the number of columns on each new row. Practically means
      // only the last row is considered.
      columns = 0;

      String line = worldScanner.nextLine();
      Scanner lineScanner = new Scanner(line);

      int nrOfUsers = 0;

      // parse the row tile for tile
      while(lineScanner.hasNext()) {
        columns++;
        String tile = lineScanner.next();

        // respond according to what tile is specified
        switch (tile) {
          case "m":
            Tile player = new MonkeyTile();
            player.setTag("m" + nrOfUsers++); // Set a unique id
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

    // The actual tile grid we're gonna return
    Tile[][] tileGrid = new Tile[rows][columns];

    // Copy over the tiles to the grid
    for (int y = 0; y< tileGrid.length; y++) {
      for (int x = 0; x < tileGrid[y].length; x++) {
        tileGrid[y][x] = tiles.get(y*columns+x);
      }
    }

    return tileGrid;
  }

  /**
   * Extract the n'th world from a world specification file containing one or more.
   *
   * @param worldNr The desired world counted from the top. 0-indexed.
   * @return A String containing a single world specification.
   * @throws FileNotFoundException
   */
  private String extractWorld(int worldNr) throws FileNotFoundException {
    Scanner scanner = new Scanner(worldFile);
    // This is the magic. By delimiting on single comment lines we can match each
    // world in between. Only permits a single line comment for each world.
    scanner.useDelimiter("(#.*)");
    int skippedWorlds = 0;

    // Skip forward until the sought world.
    while (scanner.hasNext() && worldNr > skippedWorlds) {
      scanner.next();
      skippedWorlds++;
    }

    // Return the world without superfluous whitespace.
    return scanner.next().trim();
  }
}
