package com.monkeymusicchallenge.warmup;

import org.json.JSONObject;

/**
 * The basic interface by which we receive information about the game world
 * and respond with commands.
 */
public interface Ai {
    public String move(final JSONObject gameState);
}