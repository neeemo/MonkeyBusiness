package com.monkeymusicchallenge.warmup.graph;

/**
 * Created by Daniel on 11/4/2014.
 */
public class Edge<E> {
  private final Vertex<E> source;
  private final Vertex<E> destination;
  private final int weight;

  public Edge(Vertex<E> source, Vertex<E> destination, int weight) {
    this.source = source;
    this.source.getOutgoing().add(this);
    this.destination = destination;
    this.destination.getIncoming().add(this);
    this.weight = weight;
  }

  public Vertex<E> getSource() {
    return source;
  }

  public Vertex<E> getDestination() {
    return destination;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return source + " --(" + weight + ")-> " + destination;
  }
}

