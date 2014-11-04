package com.monkeymusicchallenge.warmup.strategies;

import com.monkeymusicchallenge.warmup.Ai;
import com.monkeymusicchallenge.warmup.Point;
import com.monkeymusicchallenge.warmup.State;
import com.monkeymusicchallenge.warmup.graph.StateGraph;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.PriorityQueue;

/**
 * Created by Daniel on 11/1/2014.
 */
public class Dijkstra implements Ai {
  @Override
  public String move(JSONObject gameState) {
    StateGraph<State> graph = new StateGraph<>();

    return null;
  }
}
