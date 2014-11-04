package com.monkeymusicchallenge.warmup.graph;

import java.util.HashMap;

/**
 * Created by Daniel on 11/4/2014.
 */
public class Edge<E> {
  private final Vertex<E> source;
  private final Vertex<E> destination;
  private final int weight;

  private final HashMap<String, String> tags;

  public Edge(Vertex<E> source, Vertex<E> destination, int weight) {
    this.source = source;
    this.source.getOutgoing().add(this);
    this.destination = destination;
    this.destination.getIncoming().add(this);
    this.weight = weight;
    tags = new HashMap<>();
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

  public String putTag(String tag, String value) {
    return tags.put(tag, value);
  }

  public String getTag(String tag) {
    return tags.get(tag);
  }

  public String removeTag(String tag) {
    return tags.remove(tag);
  }

  public boolean hasTag(String tag) {
    return tags.containsKey(tag);
  }

  @Override
  public String toString() {
    return source + " --(" + weight + ")-> " + destination;
  }
}

