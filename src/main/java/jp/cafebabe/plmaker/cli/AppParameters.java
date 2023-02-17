package jp.cafebabe.plmaker.cli;

import jp.cafebabe.plmaker.GraphQLScript;
import jp.cafebabe.plmaker.PLMakerException;
import jp.cafebabe.plmaker.entities.Product;
import jp.cafebabe.plmaker.ScriptLoader;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppParameters implements Arguments {
    @Option(names = { "-g", "--graphql" }, description = "Specify the file for request GraphQL to GitHub.", hidden = true, paramLabel = "FILE")
    private Optional<Path> graphql = Optional.empty();

    @Parameters(arity = "1...*", description = "The owner and product names in GitHub.", paramLabel = "OWNER/PRODUCTs")
    private List<String> params = new ArrayList<>();

    public void validate(CommandSpec spec) {
        if(graphql.map(f -> !Files.isRegularFile(f)).orElse(false))
            throw new ParameterException(spec.commandLine(),
                    String.format("%s: not exist or not regular file", graphql.get()));
        validateParams(spec);
    }

    public Stream<Product> params() {
        return params.stream()
                .map(Product::of);
    }

    public GraphQLScript script() {
        var loader = new ScriptLoader();
        try {
            if (graphql.isPresent())
                return loader.load(graphql.get());
            return loader.loadDefault();
        } catch(IOException e) {
            throw new PLMakerException(e.getMessage());
        }
    }

    public void postprocess() {
    }

    private void validateParams(CommandSpec spec) {
        if(params.size() == 0)
            throw new ParameterException(spec.commandLine(), "missing products");
        var list = params.stream()
                .filter(this::validateParam).toList();
        if(list.size() > 0)
            throw new ParameterException(spec.commandLine(),
                    String.format("%s: product name should form OWNER/PRODUCT",
                            list.stream().collect(Collectors.joining(", "))));
    }

    private boolean validateParam(String argument) {
        String[] items = argument.split("/");
        if(items.length != 2)
            return true;
        return false;
    }
}