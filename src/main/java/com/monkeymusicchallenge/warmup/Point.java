package com.monkeymusicchallenge.warmup;

/**
 * Created by Daniel on 11/2/2014.
 */
public class Point {
  public final int x;
  public final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point setX(int x) {
    return new Point(x, this.y);
  }

  public Point setY(int y) {
    return new Point(this.x, y);
  }
}
