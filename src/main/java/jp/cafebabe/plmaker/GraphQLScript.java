package jp.cafebabe.plmaker;

import jp.cafebabe.plmaker.entities.Product;
import jp.cafebabe.plmaker.entities.Result;
import jp.cafebabe.plmaker.entities.builder.ResultBuilder;
import jp.cafebabe.plmaker.token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public class GraphQLScript {
    private String script;
    private HttpClient client;

    private GraphQLScript(String script) {
        this.script = script;
        this.client = buildClient();
    }

    public Result sendRequest(Token token, Product product) throws IOException, InterruptedException {
        var response = client.send(buildRequest(token, product), HttpResponse.BodyHandlers.ofString());
        return new ResultBuilder().build(response);
    }

    private HttpClient buildClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private HttpRequest buildRequest(Token token, Product product) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/graphql"))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("bearer %s", token.toString()))
                .POST(publisher(product))
                .build();
    }

    private HttpRequest.BodyPublisher publisher(Product product) {
        return HttpRequest.BodyPublishers.ofString(
                String.format("{\"query\":\"%s\", \"variables\":{\"owner\":\"%s\",\"name\":\"%s\"}}",
                        script, product.owner(), product.repository()));
    }

    public static GraphQLScript load(BufferedReader in) {
        return new GraphQLScript(in.lines()
                .collect(Collectors.joining(" ")));
    }
}
