package jp.cafebabe.plmaker.cli;

import jp.cafebabe.plmaker.token.Store;
import jp.cafebabe.plmaker.token.Token;
import jp.cafebabe.plmaker.token.TokenBuilder;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;

import java.util.Optional;


public class TokenParameters implements Arguments {
    @Option(names = { "-t", "--token" }, description = "Specify the token for the GitHub API.  This product reads the GitHub token from this option value, ./.plmaker.json, and ~/.config/plmaker/config.json in this order.", paramLabel = "TOKEN")
    private String token;

    @Option(names = { "--store" }, description = "If the value of this option is HOME or CWD, after running the plmaker, store the config file in the specified location. Default: NONE, Available: HOME, CWD, NONE", paramLabel = "STORE_TYPE")
    private Store store = Store.NONE;

    @Option(names = { "--overwrite" }, defaultValue = "false", paramLabel = "TRUE/FALSE", description = "If this value is TRUE, plmaker overwrite the configuration file.")
    private boolean overwrite = false;

    Optional<Token> actual;

    public void validate(CommandSpec spec) {
        readToken();
        if(actual.isEmpty())
            throw new ParameterException(spec.commandLine(), "no GitHub token. use --token option to specify the GitHub token");
    }

    public Token token() {
        return actual.get();
    }

    private void readToken() {
        if(token != null)
            actual = new TokenBuilder.Cli(token).build();
        else
            actual = new TokenBuilder.Default().build();
    }

    public void postprocess() {
        store.store(actual.get(), overwrite);
    }
}
