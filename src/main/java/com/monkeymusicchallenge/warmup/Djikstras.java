package com.monkeymusicchallenge.warmup;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Daniel on 11/1/2014.
 */
public class Djikstras {
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


}
