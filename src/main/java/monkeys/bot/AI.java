package main.java.monkeys.bot;

public class AI {

    private String randomDirection () {
        String[] directions = { "up", "down", "left", "right" };
        return directions[(int)(Math.random() * 4)];
    }

    public String move(GameState gameState) {
        return this.randomDirection();
    }
}
