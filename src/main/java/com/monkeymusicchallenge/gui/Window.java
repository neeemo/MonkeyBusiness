package com.monkeymusicchallenge.gui;

import com.monkeymusicchallenge.gui.tiles.*;
import com.monkeymusicchallenge.warmup.Ai;
import com.monkeymusicchallenge.warmup.Point;
import com.monkeymusicchallenge.warmup.strategies.RandomWalk;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The game field and logic.
 *
 * Created by Daniel on 11/2/2014.
 */
public class Window extends JFrame {
  private int currentPlayer; // alternates with each turn
  private int nrOfPlayers;
  private Tile[][] world = new Tile[0][0]; // the tile grid
  private int nrOfTurns = 30; // The sum of all players turns.
  private int winner = -1;

  /**
   * Create the initial game world and show it in a window.
   *
   * @param worldNr The desired world from the world specification file.
   */
  public Window(int worldNr) {
    WorldFactory factory = new WorldFactory("worlds");

    try {
      world = factory.createWorld(worldNr); // parse the world
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // Display tiles in a grid
    setLayout(new GridLayout(world[0].length, world.length));

    nrOfPlayers = calcNrOfPlayers();
    currentPlayer = 0;

    // adjust the number of turns based on nr of players
    nrOfTurns *= nrOfPlayers;

    // Add all tiles.
    updateGui();

    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * A really, really stupid method which removes all tiles from the JFrame
   * and then re-adds them in the correct position.
   */
  private void updateGui() {
    // remove the tiles.
    for (Tile[] row : world) {
      for (Tile tile : row) {
        remove(tile);
      }
    }

    // re-add the tiles
    for (Tile[] row : world) {
      for (Tile tile : row) {
        add(tile);
      }
    }

    // Make sure the gui is updated accordingly.
    revalidate();
    repaint();
  }

  /**
   * Advances the game one turn for the current player. Turns are
   * expected to be taken alternately for the two players.
   *
   * @param command Either "left", "right", "up" or "down"
   */
  public void takeTurn(String command) {
    nrOfTurns--;
    // Get the active player's monkey
    MonkeyTile player = (MonkeyTile) getTileByTag("m" + currentPlayer);
    // Find the position of the monkey.
    Point position = getPositionOfTile(player);

    // Try to move according to the command.
    // Stupid commands are simply disregarded.
    switch (command) {
      case "left":
        moveTo(position.x, position.y, position.x-1, position.y);
        break;
      case "right":
        moveTo(position.x, position.y, position.x+1, position.y);
        break;
      case "up":
        moveTo(position.x, position.y, position.x, position.y-1);
        break;
      case "down":
        moveTo(position.x, position.y, position.x, position.y+1);
        break;
    }

    // Rearrange the gui accordingly
    updateGui();

    // alternate the currently active player
    currentPlayer = (currentPlayer + 1) % nrOfPlayers;
  }

  /**
   * Handles the actual movement of a monkey tile.
   *
   * @param currentX The monkey's current x-coordinate.
   * @param currentY The monkey's current y-coordinate.
   * @param targetX The desired future x-coordinate of the monkey.
   * @param targetY The desired future y-coordinate of the monkey.
   */
  private void moveTo(int currentX, int currentY, int targetX, int targetY) {
    // Abandon ship if the target is out of bounds.
    if (targetX < 0 || world[0].length <= targetX || targetY < 0 || world.length <= targetY) {
      return;
    }

    // Get the monkey tile.
    MonkeyTile monkey = (MonkeyTile) world[currentY][currentX];
    // Get the target tile.
    Tile target = world[targetY][targetX];

    // Act according to the target tile.
    if (target instanceof WallTile) {
      return;  // Stupid movement, do nothing.
    } else if (target instanceof GrassTile) {
      // Simply move the monkey.
      world[currentY][currentX] = target;
      world[targetY][targetX] = monkey;
    } else if (target instanceof SongTile) {
      // Pick up the item.
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("song");
    } else if (target instanceof AlbumTile) {
      // Pick up the item.
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("album");
    } else if (target instanceof PlaylistTile) {
      // Pick up the item.
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("playlist");
    } else if (target instanceof UserTile) {
      remove(target);
      world[targetY][targetX] = new GrassTile();

      if (callWinner()) { // Check if game is won
        this.winner = currentPlayer;
      }
    }

  }

  /**
   * Go through the tiles and check that
   */
  private boolean callWinner() {
    // Check that all song/album/playlist-tiles are gone from the world.
    for (Tile[] tiles : world) {
      for (Tile tile : tiles) {
        if (!(tile instanceof GrassTile || tile instanceof WallTile || tile instanceof MonkeyTile)) {
          System.out.println("Loser!");
          return false; // Quit early
        }
      }
    }
    System.out.println("Winner!");
    return true;
  }

  /**
   * Search through the world for a tile with the specified tag.
   * @param tag The tag to search for.
   * @return The first Tile with the specified tag. Otherwise null.
   */
  private Tile getTileByTag(String tag) {
    for (Tile[] tiles: world) {
      for (Tile tile: tiles) {
        if (tile.hasTag(tag)) {
          return tile;
        }
      }
    }

    return null;
  }

  /**
   * Find the position in the world of a known tile.
   *
   * @param tile The tile for which the position is sought.
   * @return The Point of the specified tile.
   */
  private Point getPositionOfTile(Tile tile) {
    for (int y = 0; y < world.length; y++) {
      for (int x = 0; x < world[y].length; x++) {
        if (tile == world[y][x]) {
          return new Point(x, y); // Quit early
        }
      }
    }
    return null; // No such tile found.
  }

  /**
   * Count the number of monkeys in the world.
   *
   * @return The number of monkeys/players in the world.
   */
  private int calcNrOfPlayers() {
    int nrOfPlayers = 0;
    for (Tile[] tiles: world) {
      for (Tile tile: tiles) {
        if (tile instanceof MonkeyTile) {
          nrOfPlayers++;
        }
      }
    }
    return nrOfPlayers;
  }

  /**
   * Convert the world state to a JSONObject that can be sent to an
   * Ai-implementation.
   *
   * @return A JSONObject of the world state.
   */
  private JSONObject makeState() {
    JSONObject json = new JSONObject();
    MonkeyTile monkey = (MonkeyTile) getTileByTag("m" + currentPlayer);
    Point position = getPositionOfTile(monkey);

    // Convert the world row by row to json
    for (Tile[] row : world) {

      JSONArray array = new JSONArray();

      // Convert the Tile-array to a String-array so the JSON-
      // library knows what to do.
      for (Tile tile : row) {
        array.put(tile.toString());
      }

      // Append each row to the layout property.
      json.append("layout", array);
    }

    // Append the monkey's position.
    json.append("position", position.y);
    json.append("position", position.x);

    // Check if player has won the game.
    json.put("win", currentPlayer == winner);

    // Calculate the individual players turn based on the global nr of turns.
    int playerTurn = (int) (nrOfTurns/nrOfPlayers + (1-1/nrOfPlayers));
    json.put("turns", playerTurn);

    // Add all the monkey's picked up items.
    List<String> items = monkey.getItems();
    for (String item : items) {
      json.append("pickedUp", item);
    }

    return json;
  }

  /**
   * Run a local game.
   *
   * @param args The world index to play in.
   */
  public static void main(String[] args) {
    Window window = new Window(Integer.parseInt(args[0]));

    // The different monkey strategies in the game.
    Ai[] strategies = {
        new RandomWalk(),
        new RandomWalk()
    };

    while(window.nrOfTurns > 0) {
      // Get the players strategy.
      Ai strategy = strategies[window.currentPlayer];
      // Send it the game state and receive a command.
      JSONObject json = window.makeState();
      String command = strategy.move(window.makeState());
      // Act on the command.
      window.takeTurn(command);

      // Wait about half a second so you get visual feedback.
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Quit the game when all turns are over.
    System.exit(0);
  }
}
