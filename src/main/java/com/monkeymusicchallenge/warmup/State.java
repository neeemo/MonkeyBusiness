package com.monkeymusicchallenge.warmup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 11/1/2014.
 */
public class State {

  private final JSONObject state;
  private final JSONArray layout;
  private final Point position;
  private final String won;
  private final String turns;
  private final JSONArray items;
  private final Point opponent;

  public State(JSONObject state) {
    this.state = new JSONObject(state.toString());
    layout = state.getJSONArray("layout");
    JSONArray posArray = state.getJSONArray("position");
    this.position = new Point(posArray.getInt(1), posArray.getInt(0));
    this.won = state.getString("win");
    this.turns = state.getString("turns");
    this.items = state.getJSONArray("pickedUp");
    this.opponent = getOpponentPosition();
  }

  public boolean hasOpponent() {
    return opponent != null;
  }

  public String getAt(int x, int y) {
    try {
      return layout.getJSONArray(y).getString(x);
    } catch (JSONException e) {
      throw new IndexOutOfBoundsException();
    }
  }

  public Point getOpponentPosition() {
    for (int y = 0; y<layout.length(); y++) {
      JSONArray row = layout.getJSONArray(y);
      for (int x = 0; x<row.length(); x++) {
        String obj = row.getString(x);
        if (obj.equals("monkey") && (x != position.x || y != position.y) ) {
          return new Point(x, y);
        }
      }
    }
    return null;
  }
}
