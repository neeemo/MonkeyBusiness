package com.monkeymusicchallenge.gui;

import org.json.JSONObject;

public interface Ai {
  public String move(final JSONObject gameState);
}
