package jp.cafebabe.plmaker.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppParametersTest {
    @Test
    public void testInvalidProductName() {
        var main = new Main();
        var cl = new CommandLine(main);
        cl.parseArgs("tamada -t token".split(" "));
        assertThrows(ParameterException.class, () -> main.validate());
    }

    @Test
    public void testMissingProducts() {
        var main = new Main();
        var cl = new CommandLine(main);
        cl.execute("-t token".split(" "));
        assertThrows(ParameterException.class, () -> main.validate());
    }

    @Test
    public void testGraphQLScript() {
        var main = new Main();
        var cl = new CommandLine(main);
        cl.parseArgs("tamada/plmaker -t token -g src".split(" "));
        assertThrows(ParameterException.class, () -> main.validate());
    }
}
