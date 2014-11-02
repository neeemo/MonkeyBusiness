package com.monkeymusicchallenge.warmup;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Daniel on 11/1/2014.
 */
public class State implements Comparable<State> {
  private Point position;
  private JSONArray world;
  private List<String> commands;

  public State(JSONArray world, Point start) {
    this.world = world;
    this.position = start;
  }

  private State(JSONArray world, List<String> commands, Point position) {
    this.world = world;
    this.commands = commands;
    this.position = position;
  }

  public int cost() {
    return commands.size();
  }

  public Point getCurrentPosition() {
    return position;
  }

  public State addCommand(String command) {
    Point nextPos;

    switch (command) {
      case "left":
        nextPos = position.setX(position.x - 1);
      break;
      case "right":
        nextPos = position.setX(position.x + 1);
        break;
      case "up":
        nextPos = position.setY(position.y + 1);
        break;
      case "down": // fall through
      default:
        nextPos = position.setY(position.y - 1);
        break;
    }

    String thing = "";

    switch (world.getJSONArray(nextPos.y).getString(nextPos.x)) {
      case "user":
        break;
      case "monkey":
        break;
      case "playlist":
        break;
      case "wall":
        break;
      case "album":
        break;
      case "song":
        break;
    }


    return null;
  }

  @Override
  public int compareTo(State other) {
    return (new Integer(this.cost())).compareTo(other.cost());
  }
}
