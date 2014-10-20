package main.java.monkeys.bot;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Client {

    private String uri;
    private String team;
    private String payloadApiKey;
    private CloseableHttpClient httpClient;

    private Gson gson = new Gson();

    public Client (String uri, String team, String apiKey) {
        this.uri = uri;
        this.payloadApiKey = apiKey;
        this.team = team;
        this.httpClient = HttpClients.createDefault();
    }

    public GameState post(Payload payload) throws Exception {
        HttpUriRequest post = RequestBuilder.post()
                .addHeader("Content-Type", "application/json")
                .setUri(this.uri + this.team)
                .setEntity(new StringEntity(gson.toJson(payload)))
                .build();

        CloseableHttpResponse response = this.httpClient.execute(post);
        int status = response.getStatusLine().getStatusCode();
        String body = EntityUtils.toString(response.getEntity());

        if (status != 200) {
            throw new Exception("Post request not OK: " + body);
        }
        GameState gameState = gson.fromJson(body, GameState.class);
        return gameState;
    }

    public class Payload {

        private String command;
        private String apiKey;

        public Payload(String command) {
            this.apiKey = payloadApiKey;
            this.command = command;
        }
    }

    public class MovePayload extends Payload {

        private String direction;

        public MovePayload(String command, String direction) {
            super(command);
            this.direction = direction;
        }
    }
}
