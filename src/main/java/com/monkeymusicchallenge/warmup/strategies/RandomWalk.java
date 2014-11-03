package com.monkeymusicchallenge.warmup.strategies;

import com.monkeymusicchallenge.warmup.Ai;
import org.json.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Daniel on 11/2/2014.
 */
public class RandomWalk implements Ai {
  @Override
  public String move(JSONObject gameState) {
    return this.randomDirection();
  }

  private String randomDirection() {
    return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
  }
}
