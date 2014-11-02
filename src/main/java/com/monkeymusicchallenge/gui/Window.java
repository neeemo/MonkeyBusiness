package com.monkeymusicchallenge.gui;

import com.monkeymusicchallenge.gui.tiles.*;
import com.monkeymusicchallenge.warmup.Point;
import com.monkeymusicchallenge.warmup.Strategies.RandomWalk;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by Daniel on 11/2/2014.
 */
public class Window extends JFrame {

  private int currentPlayer;
  private int nrOfPlayers;
  private Tile[][] world = new Tile[0][0];
  private int nrOfTurns = 30;
  private List<Ai> strategies;

  public Window(int worldNr) {
    WorldFactory factory = new WorldFactory("worlds");

    try {
      world = factory.createWorld(worldNr);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    setLayout(new GridLayout(world[0].length, world.length));

    nrOfPlayers = calcNrOfPlayers();
    currentPlayer = 0;

    updateGui();

    strategies = new ArrayList<>();

    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void updateGui() {
    // remove
    for (Tile[] row : world) {
      for (Tile tile : row) {
        remove(tile);
      }
    }

    // re-add
    for (Tile[] row : world) {
      for (Tile tile : row) {
        add(tile);
      }
    }
    revalidate();
    repaint();
  }

  public void takeTurn(String command) {
    nrOfTurns--;
    currentPlayer = (currentPlayer + 1) % nrOfPlayers;
    MonkeyTile player = (MonkeyTile) getTileByTag("m" + currentPlayer);
    Point position = getPositionOfTile(player);

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

    updateGui();
  }

  private void moveTo(int currentX, int currentY, int targetX, int targetY) {
    if (targetX < 0 || world[0].length <= targetX || targetY < 0 || world.length <= targetY) {
      return;
    }

    MonkeyTile monkey = (MonkeyTile) world[currentY][currentX];
    Tile target = world[targetY][targetX];

    if (target instanceof WallTile) {
      return;
    } else if (target instanceof GrassTile) {
      world[currentY][currentX] = target;
      world[targetY][targetX] = monkey;
    } else if (target instanceof SongTile) {
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("song");
    } else if (target instanceof AlbumTile) {
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("album");
    } else if (target instanceof PlaylistTile) {
      remove(target);
      world[targetY][targetX] = new GrassTile();
      monkey.addSwag("playlist");
    } else if (target instanceof UserTile) {
      remove(target);
      world[targetY][targetX] = new GrassTile();
      System.out.println("Winner!");
    }

  }

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

  private Point getPositionOfTile(Tile tile) {
    for (int y = 0; y < world.length; y++) {
      for (int x = 0; x < world[y].length; x++) {
        if (tile == world[y][x]) {
          return new Point(x, y);
        }
      }
    }
    return null;
  }

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

  public int getNrOfPlayers() {
    return nrOfPlayers;
  }

  private JSONObject makeState() {
    JSONObject json = new JSONObject();
    MonkeyTile monkey = (MonkeyTile) getTileByTag("m" + currentPlayer);
    Point position = getPositionOfTile(monkey);

    for (Tile[] row : world) {
      String[] rowString = new String[row.length];

      for (int i = 0; i < rowString.length; i++) {
        rowString[i] = row[i].toString();
      }

      json.append("layout", rowString);
    }

    json.append("position", position.y);
    json.append("position", position.x);

    List<String> swag = monkey.getSwag();
    for (String item : swag) {
      json.append("pickedUp", item);
    }

    return json;
  }

  public static void main(String[] args) {
    Window window = new Window(Integer.parseInt(args[0]));

    Ai[] strategies = {
        new RandomWalk()
    };

    for (int i = window.nrOfPlayers*window.nrOfTurns; i > 0; i--) {
      Ai strategy = strategies[window.currentPlayer];
      String command = strategy.move(window.makeState());
      window.takeTurn(command);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.exit(0);
  }
}
