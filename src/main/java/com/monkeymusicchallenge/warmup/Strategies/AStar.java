package com.monkeymusicchallenge.warmup.strategies;

import com.monkeymusicchallenge.warmup.Ai;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
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
    private List<Node> pathToTarget;
    private int steps;

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
        steps = 0;

        updateTiles();
        initiateNodes();
        setAdjancies();


        for(Node n : nodes){
            if(n.value.equals("user")){
                search(nodes.get(0), n);
                pathToTarget = printPath(n);
                //System.out.println(pathToTarget);
            }
        }


    }

    //If we want to update tiles or recalculate every step something can happen in this method
    public void updateWorld(JSONObject world){
        this.world = world;

    }

    public List<Node> printPath(Node target){
        List<Node> path = new ArrayList<Node>();

        for(Node node = target; node != null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    };

    public void search(Node source, Node target){
        Set<Node> explored = new HashSet<Node>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>() {
                    @Override
                    public int compare(Node i, Node j) {
                        if(i.f_scores > j.f_scores){
                            return 1;
                        }
                        else if(i.f_scores < j.f_scores){
                            return -1;
                        }
                        else{
                            return 0;
                        }
                    }
                });
        //start cost is 0 at beginning
        source.g_scores = 0;
        queue.add(source);
        boolean found = false;

        while((!queue.isEmpty())&&(!found)){
            //the node with lowest f_score
            Node current = queue.poll();
            explored.add(current);

            //target found
            if(current.value.equals(target.value)){
                found = true;
            }

            //check every child of current node
            for(Edge e : current.adjacencies){
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;

                //if child node has been evaluated and newer f_score is higher, skip/break
                if((explored.contains(child) &&
                        (temp_f_scores >= child.f_scores))){
                        continue;
                }
                else if((!queue.contains(child)) ||
                        (temp_f_scores < child.f_scores)){

                        child.parent = current;
                        child.g_scores = temp_g_scores;
                        child.f_scores = temp_f_scores;

                        if(queue.contains(child)){
                            queue.remove(child);
                        }

                        queue.add(child);
                }
            }
        }
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
                        nodes.add(new Node("monkey" + (row + ":" +col), calculateDistance(col, row, findTile("user", "col"), findTile("user", "row"))));
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
                //System.out.println("Row: " + row + " Col: " + col + " = " + nodes.get(printrow).value);
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
                    if (row < rowSize - 1) {
                        if(!nodes.get(index + 1).value.equals("wall"))
                            edgeList.add(new Edge(nodes.get(index + 1), 1));
                    }
                    if (col < colSize - 1) {
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
        /*
        //Just a test print to find Adjacencies to a tile/node
        for(int i = 0; i < nodes.size(); i++){
            if(nodes.get(i).value.equals("empty3:4")){
                for(Edge e: nodes.get(i).adjacencies){
                    System.out.println(e.target);
                }

            }
        }
        */
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

    public String getDirectionFromNodes(Node source, Node target){
        String s = source.value.replaceAll("[A-Za-z]", "");
        String t = target.value.replaceAll("[A-Za-z]", "");
        System.out.println("source " + s + " target " + t);
        if(Character.getNumericValue(s.charAt(0)) < Character.getNumericValue(t.charAt(0))){
            return "down";
        }
        else if(Character.getNumericValue(s.charAt(0)) > Character.getNumericValue(t.charAt(0))){
            return "up";
        }
        else if(Character.getNumericValue(s.charAt(2)) < Character.getNumericValue(t.charAt(2))){
            return "right";
        }
        else if(Character.getNumericValue(s.charAt(2)) > Character.getNumericValue(t.charAt(2))){
            return "left";
        }
        return "error";
    }

    @Override
    public String move(JSONObject gameState) {


        return this.aStarMovement();
        //Temporary return
        //return this.randomDirection(gameState.getInt("turns"));
    }

    //Temporary code for NullPointerException bug
    private String randomDirection(int turn) {
        return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
    }

    private String aStarMovement(){
        String direction = getDirectionFromNodes(pathToTarget.get(steps), pathToTarget.get(steps+1));
        if(direction.equals("error")){
            System.out.println("Something went wrong! Shutting down");
            System.exit(0);
        }
        steps++;
        return direction;
    }
}
