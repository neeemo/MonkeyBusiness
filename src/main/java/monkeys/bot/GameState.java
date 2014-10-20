package main.java.monkeys.bot;

public class GameState {

    // A position [y,x]
    public final int[] position;
    // A matrix of rows containing tiles
    public final String[][] layout;
    // Number of remaining turns
    public final int turns;
    // The different items you have picked up
    public final String[] pickedUp;
    // A hint for how you should accomplish the problem
    public final String hint;
    // Did you win the game?
    public final boolean win;

    public GameState(int[] position,
                     String[][] layout,
                     int turns,
                     String[] pickedUp,
                     String hint,
                     boolean win) {

        this.position = position;
        this.layout = layout;
        this.turns = turns;
        this.pickedUp = pickedUp;
        this.hint = hint;
        this.win = win;
    }
}
