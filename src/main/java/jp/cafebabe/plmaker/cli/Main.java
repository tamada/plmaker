package jp.cafebabe.plmaker.cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import jp.cafebabe.plmaker.GraphQLScript;
import jp.cafebabe.plmaker.PLMakerException;
import jp.cafebabe.plmaker.entities.Product;
import jp.cafebabe.plmaker.entities.Result;
import jp.cafebabe.plmaker.token.Token;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

@Command(name = "plmaker", mixinStandardHelpOptions = true, versionProvider = PLMaker.class)
public class Main implements Callable<Integer> {
    @Mixin
    private TokenParameters tokens;

    @Mixin
    private AppParameters args;

    @Spec
    private CommandSpec spec;

    @Override
    public Integer call() {
        validate();
        int status = perform();
        postprocess();
        return status;
    }

    public Token token() {
        return tokens.actual.get();
    }

    private int perform() {
        Token token = tokens.token();
        var script = args.script();
        try(ResultPrinter rp = new ResultPrinter(spec.commandLine().getOut())) {
            args.params()
                    .map(product -> send(script, token, product))
                    .forEach(r -> r.accept(rp));
            return 0;
        }
    }

    private Result send(GraphQLScript script, Token token, Product product) {
        try {
            return script.sendRequest(token, product);
        } catch(IOException | InterruptedException e) {
            Logger.getLogger("plmaker").warning(e.getMessage());
            throw new PLMakerException(e.getMessage());
        }
    }

    public void postprocess() {
        tokens.postprocess();
        args.postprocess();
    }

    public void validate() {
        tokens.validate(spec);
        args.validate(spec);
    }

    public static void main(String[] args) {
        new CommandLine(new Main())
            .setCaseInsensitiveEnumValuesAllowed(true)
            .setParameterExceptionHandler(new UsageHandler())
            .execute(args);
    }
}
