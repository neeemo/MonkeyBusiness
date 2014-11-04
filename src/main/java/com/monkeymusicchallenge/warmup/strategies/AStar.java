package com.monkeymusicchallenge.warmup.strategies;

import com.monkeymusicchallenge.warmup.Ai;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neeemo on 03/11/14.
 */
public class AStar implements Ai {
    private JSONObject world;
    private JSONArray jsonArray;
    private ArrayList<String> list;
    private String[][] stringTile;
    private ArrayList<Node> nodes;
    private Set<Node> explored;


    //Node class, we can put this in a separate head file when done
    class Node{
        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;

        public Node(String val, double hVal){
            value = val;
            h_scores = hVal;
        }

        public String toString(){
            return value;
        }

    }

    //Edge class, we can put this in a separate head file when done
    class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
            target = targetNode;
            cost = costVal;
        }
    }

    //Constructor calls methods etc.
    public AStar(JSONObject world){
        this.world = world;
        jsonArray = world.getJSONArray("layout");
        nodes = new ArrayList<Node>();
        stringTile = new String[jsonArray.getJSONArray(0).length()][jsonArray.length()];
        list = new ArrayList<String>();
        explored = new HashSet<Node>();

        updateTiles();
        initiateNodes();
        setAdjancies();
    }

    //If we want to update tiles or recalculate every step something can happen in this method
    public void updateWorld(JSONObject world){
        this.world = world;

    }

    //update tiles
    public void updateTiles(){
        list.clear();
        jsonArray = world.getJSONArray("layout");
        if(jsonArray != null){
            int len = jsonArray.length();
            for(int i = 0; i < len; i++){
               list.add(jsonArray.get(i).toString());
            }
            int col = 0;
            //ugly but efficient way of checking col size in a string
            String colChars = Arrays.toString(list.get(0).split(","));
            while(col < (colChars.length()-colChars.replace(",", "").length()+1)) {
                int row = 0;
                for (String s : list) {
                    String[] temp = s.split(",");
                    String temp2 = temp[col].replaceAll("[^A-Za-z0-9]", "");
                    switch (temp2) {
                        case "monkey":
                            stringTile[row][col] = "monkey";
                            break;
                        case "empty":
                            stringTile[row][col] = "empty";
                            break;
                        case "playlist":
                            stringTile[row][col] = "playlist";
                            break;
                        case "song":
                            stringTile[row][col] = "song";
                            break;
                        case "wall":
                            stringTile[row][col] = "wall";
                            break;
                        case "user":
                            stringTile[row][col] = "user";
                            break;
                        case "album":
                            stringTile[row][col] = "album";
                            break;
                    }
                    row++;
                }
                col++;
            }
        }
    }

    //creates a list of nodes
    public void initiateNodes(){
        int col = 0;
        int printrow = 0;
        String colChars = Arrays.toString(list.get(0).split(","));
        while(col < (colChars.length()-colChars.replace(",", "").length()+1)){
            int row = 0;
            while(row < list.size()){
                String tile = stringTile[row][col];
                switch(tile){
                    case "monkey":
                        nodes.add(new Node("monkey", calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
                        break;
                    case "empty":
                        nodes.add(new Node("empty" + (row + ":" +col), calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
                        break;
                    case "playlist":
                        nodes.add(new Node("playlist" + (row + ":" +col), calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
                        break;
                    case "song":
                        nodes.add(new Node("song" + (row + ":" +col), calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
                        break;
                    case "wall":
                        nodes.add(new Node("wall", 999));
                        break;
                    case "user":
                        nodes.add(new Node("user", 0));
                        break;
                    case "album":
                        nodes.add(new Node("album" + (row + ":" +col), calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
                        break;
                }
                System.out.println("Row: " + row + " Col: " + col + " = " + nodes.get(printrow).value);
                //System.out.println(nodes.get(printrow).value + " distance to user: " + nodes.get(printrow).h_scores);
                row++;
                printrow++;
            }
            col++;
        }
    }

    //set which nodes that can travel to each other
    public void setAdjancies(){
        int col = 0;
        int index = 0;
        String colChars = Arrays.toString(list.get(0).split(","));
        int colSize = (colChars.length() - colChars.replace(",", "").length()+1);
        int rowSize = list.size();
        while (col < colSize) {
            int row = 0;
            while (row < rowSize) {
                if(nodes.get(index).value != "wall") {
                        ArrayList<Edge> edgeList = new ArrayList<Edge>();
                        //Check possible adjacencies and adds to list
                        if (row != 0) {
                            if(!nodes.get(index - 1).value.equals("wall"))
                                edgeList.add(new Edge(nodes.get(index - 1), 1));
                        }
                        if (col != 0) {
                            if(!nodes.get(index - rowSize).value.equals("wall"))
                                edgeList.add(new Edge(nodes.get(index - colSize), 1));
                        }
                        if (row < rowSize - 2) {
                            if(!nodes.get(index + 1).value.equals("wall"))
                                edgeList.add(new Edge(nodes.get(index + 1), 1));
                        }
                        if (col < colSize - 2) {
                            if(!nodes.get(index + colSize).value.equals("wall"))
                                edgeList.add(new Edge(nodes.get(index + colSize), 1));
                        }

                        //creates allowed adjacencies from list to node
                        nodes.get(index).adjacencies = new Edge[edgeList.size()];
                        for (int i = 0; i < edgeList.size(); i++) {
                            nodes.get(index).adjacencies[i] = edgeList.get(i);
                        }
                }
                row++;
                index++;
            }
            col++;
        }

        //Just a test print to find Adjacencies to a tile/node
        for(int i = 0; i < nodes.size(); i++){
            if(nodes.get(i).value.equals("monkey")){
                for(Edge e: nodes.get(i).adjacencies){
                    System.out.println(e.target);
                }

            }
        }
    }


    //Want to have dijistras here or something
    public int calculateSteps(int sx, int sy, int tx, int ty){
        return 0;
    }

    /*
    Calculated bird distance
    Used at the moment but should be a calculatingStep path finding instead to determine real cost
     */
    public float calculateDistance(int sx, int sy, int tx, int ty){
        float x = Math.abs(sx - tx);
        float y = Math.abs(sy - ty);
        float diagonal = (float) Math.sqrt((Math.pow(x, 2)+Math.pow(y, 2)));
        return diagonal;
    }

    //Return a tiles row or col value
    public int findTile(String s, String xy) {
        int col = 0;
        String colChars = Arrays.toString(list.get(0).split(","));
        while (col < (colChars.length() - colChars.replace(",", "").length() + 1)) {
            int row = 0;
            while (row < list.size()) {
                if (stringTile[row][col].equals(s)) {
                    if (xy.equals("col")) {
                        return col;
                    } else if (xy.equals("row")) {
                        return row;
                    }
                }
                row++;
            }
            col++;
        }
        return 0;
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
