package jp.cafebabe.plmaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptLoader {
    public GraphQLScript load(Path path) throws IOException {
        try(var in = Files.newBufferedReader(path)) {
            return GraphQLScript.load(in);
        }
    }

    public GraphQLScript load (URL url) throws IOException {
        try(var in = url.openStream()) {
            return GraphQLScript.load(toReader(in));
        }
    }

    public GraphQLScript loadDefault() throws IOException {
        try(var in = ScriptLoader.class.getResourceAsStream("/resources/github.graphql")) {
            return GraphQLScript.load(toReader(in));
        }
    }

    private BufferedReader toReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }
}
