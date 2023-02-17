package jp.cafebabe.plmaker.token;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class manages the token.
 * This class read token from configuration file, or command line interface by the following order.
 * <ul>
 *     <li><code>$HOME/.config/plmaker/config.json</code></li>
 *     <li><code>$PWD/.plmaker.json</code></li>
 *     <li>CLI option</li>
 * </ul>
 */
public interface TokenBuilder {
    Optional<Token> build();

    class Cli implements TokenBuilder {
        private String token;

        public Cli(String token) {
            this.token = token;
        }

        public Optional<Token> build() {
            return Optional.of(new Token(token));
        }
    }

    class Default implements TokenBuilder {
        private Map<Path, Function<Path, Optional<Token>>> processor = new LinkedHashMap<>();

        public Default() {
            processor.put(Store.CWD.path(), this::buildFromFile);
            processor.put(Store.HOME.path(), this::buildFromFile);
        }

        @Override
        public Optional<Token> build() {
            return processor.entrySet().stream()
                    .map(this::buildImpl)
                    .filter(Optional::isPresent)
                    .findFirst().orElse(Optional.empty());
        }

        private Optional<Token> buildImpl(Map.Entry<Path, Function<Path, Optional<Token>>> entry) {
            if(Files.exists(entry.getKey()))
                return entry.getValue().apply(entry.getKey());
            return Optional.empty();
        }

        private Optional<Token> buildFromFile(Path path) {
            Gson gson = new Gson();
            try(var in = Files.newBufferedReader(path)) {
                return Optional.of(gson.fromJson(in, Token.class));
            } catch(IOException e) {
                throw new InternalError(e);
            }
        }
    }
}
