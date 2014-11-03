package com.monkeymusicchallenge.warmup.Strategies;

import com.monkeymusicchallenge.warmup.Ai;
import com.monkeymusicchallenge.warmup.Point;
import com.monkeymusicchallenge.warmup.State;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.PriorityQueue;

/**
 * Created by Daniel on 11/1/2014.
 */
public class Djikstras implements Ai{
  private final PriorityQueue<State> queue;
  private final State path;

  public Djikstras(JSONArray world, Point start, Point end) {
    State root = new State(world, new Point(0,0));
    queue = new PriorityQueue<State>();
    queue.add(root);

    this.path = search(end);
  }

  private State search(Point end) {

    while (!queue.isEmpty()) {
      State state = queue.poll();

    }

    return null;
  }

  private boolean isSolution(State state, Point end) {

    return false;
  }


  @Override
  public String move(JSONObject gameState) {
    return null;
  }
}
