package com.monkeymusicchallenge.warmup.graph;

import java.util.Collection;
import java.util.HashMap;

/**
 * A state graph is a fully connected graph where vertices represent states
 * and equal vertices are assumed to be the same state. When adding a new
 * state vertex, should it already exist in the graph, the existing
 * instance will be used instead.
 *
 * To check if a state already exists in the graph, the graph hashes the
 * vertices.
 *
 * Created by Daniel on 11/4/2014.
 */
public class StateGraph<E> {
  private final HashMap<Vertex<E>, Vertex<E>> vertices;
  private final HashMap<Edge<E>, Edge<E>> edges;

  public StateGraph() {
    this.vertices = new HashMap<>();
    this.edges = new HashMap<>();
  }

  public Collection<Vertex<E>> getVertices() {
    return vertices.values();
  }

  public Collection<Edge<E>> getEdges() {
    return edges.values();
  }

  public Edge connect(Vertex<E> source, Vertex<E> destination) {
    return connect(source, destination, 1);
  }

  public Edge connect(Vertex<E> source, Vertex<E> destination, int weight) {
    if (vertices.containsKey(source)) {
      source = vertices.get(source);
    } else {
      vertices.put(source, source);
    }

    if (vertices.containsKey(destination)) {
      destination = vertices.get(destination);
    } else {
      vertices.put(destination, destination);
    }

    Edge<E> edge = new Edge<>(source, destination, weight);
    edges.put(edge, edge);

    return edge;
  }

  /**
   * Quick and dirty test in lieu of unit testing.
   * @param args Not used.
   */
  public static void main(String[] args) {
    StateGraph<String> graph = new StateGraph<>();
    Vertex<String> v1 = new Vertex<>("Vertex 1");
    Vertex<String> v2 = new Vertex<>("Vertex 2");
    graph.connect(v1, v2, 14);

    for (Vertex<String> vertex : graph.getVertices()) {
      System.out.println(vertex);
    }

    System.out.println(v1.getOutgoing().get(0));
    System.out.println("Weight: " + v1.getOutgoing().get(0).getWeight());

    System.out.println("Vertices: " + graph.getVertices().size());
    System.out.println("Edges: " + graph.getEdges().size());

    System.out.println(v1.equals(v2));
    System.out.println(v1.equals(new Vertex<String>("Vertex 1")));
    System.out.println(v1.equals(new Vertex<Integer>(1)));
  }
}
