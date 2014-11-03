package com.monkeymusicchallenge.warmup.Strategies;


import com.monkeymusicchallenge.warmup.Ai;
import com.monkeymusicchallenge.warmup.Point;
import com.monkeymusicchallenge.warmup.State;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neeemo on 03/11/14.
 */
public class AStar implements Ai {
    private JSONObject world;
    private ArrayList<String> list;



    public AStar(JSONObject world){
        this.world = world;

        parseToString();

    }

    public void updateWorld(JSONObject world){
        this.world = world;

    }

    public void parseToString(){
        //System.out.println(world.getJSONArray("layout"));


        list = new ArrayList<String>();
        JSONArray jsonArray = world.getJSONArray("layout");
        if(jsonArray != null){
            int len = jsonArray.length();
            for(int i = 0; i < len; i++){
               list.add(jsonArray.get(i).toString());
            }
            for(String s : list){
                System.out.println(s);
            }



        }
    }

    @Override
    public String move(JSONObject gameState) {
        //Temporary return
        return this.randomDirection();
    }

    //Temporary code for NullPointerException bug
    private String randomDirection() {
        return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
    }
}
