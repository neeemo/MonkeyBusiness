package main.java.monkeys.bot;

public class Main {

    public static void main(String[] args) throws Exception {
        // The server should not be changed during this challenge
        String server = System.getenv("SERVER");
        if (server == null) server = "http://warmup.monkeymusicchallenge.com/team/";

        // Read configuration from environment
        String team = null, apiKey = null;
        try {
            team = args[0];
            apiKey = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Team and api key are both required for playing this game
            if (team == null || apiKey == null) {
                System.out.println("Usage: ... Main <teamname> <apiKey>");
                System.exit(1);
            }
        }

        // Construct a new ai for monkey control
        AI ai = new AI();
        // Construct post client
        Client client = new Client(server, team, apiKey);

        // Each post request returns the new gameState.
        GameState gameState = client.post(client.new Payload("new game"));

        while (true) {
            String direction = ai.move(gameState);
            gameState = client.post(client.new MovePayload("move", direction));

            if (gameState.turns <= 0 || direction == null) {
                break;
            }
        }
        System.out.println("Game over. This is what happened:");
        System.out.println(gameState.hint);
    }
}
