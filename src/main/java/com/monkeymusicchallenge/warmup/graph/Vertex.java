package com.monkeymusicchallenge.warmup.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 11/4/2014.
 */
public class Vertex<E extends Object> {
  private final E obj;
  private final List<Edge> incoming;
  private final List<Edge> outgoing;
  private final HashMap<String, String> tags;

  public Vertex(E obj) {
    this.obj = obj;
    incoming = new ArrayList<>();
    outgoing = new ArrayList<>();
    tags = new HashMap<>();
  }

  public E getObj() {
    return this.obj;
  }

  public List<Edge> getIncoming() {
    return incoming;
  }

  public List<Edge> getOutgoing() {
    return outgoing;
  }

  public int getDegree() {
    return outgoing.size() - incoming.size();
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
  public int hashCode() {
    final int prime = 71;
    return prime * obj.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof Vertex) {
      try {
        @SuppressWarnings("unchecked")
        E otherObj = (E) ((Vertex) other).obj;
        return obj.equals(otherObj);
      } catch (ClassCastException e) {
        return false;
      }
    }

    return false;
  }

  @Override
  public String toString(){
    return "Vertex<" + obj.getClass().getSimpleName() + ">(" + obj.toString() + ")";
  }
}
