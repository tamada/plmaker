package jp.cafebabe.plmaker.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.MissingParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenParametersTest {
    @Nested
    public static class NoHomeNoCwdConfigTest {
        @BeforeEach
        public void updateHomeAndCwd() throws Exception {
            var props = System.getProperties();
            props.setProperty("user.home", "src/test/resources/homenone");
            props.setProperty("user.dir", "src/test/resources/cwdnone");
        }

        @Test
        public void testNoToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker");
            assertThrows(ParameterException.class, () -> main.validate());
         }

        @Test
        public void testWithToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }
    }

    @Nested
    public static class NoHomeCwdConfigTest {
        @BeforeEach
        public void updateHomeAndCwd() throws Exception {
            var props = System.getProperties();
            props.setProperty("user.home", "src/test/resources/homenone");
            props.setProperty("user.dir", "src/test/resources/cwd");
        }

        @Test
        public void testNoToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker");
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cwd_token\"}", token.toJson());
        }

        @Test
        public void testWithToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }
    }

    @Nested
    public static class HomeNoCwdConfigTest {
        @BeforeEach
        public void updateHomeAndCwd() throws Exception {
            var props = System.getProperties();
            props.setProperty("user.home", "src/test/resources/home");
            props.setProperty("user.dir", "src/test/resources/cwdnone");
        }

        @Test
        public void testNoToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker");
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"home_token\"}", token.toJson());
        }

        @Test
        public void testWithToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }
    }

    @Nested
    public class HomeCwdConfigTest {
        @BeforeEach
        public void updateHomeAndCwd() throws Exception {
            var props = System.getProperties();
            props.setProperty("user.home", "src/test/resources/home");
            props.setProperty("user.dir", "src/test/resources/cwd");
        }

        @Test
        public void testNoToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker");
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cwd_token\"}", token.toJson());
        }

        @Test
        public void testWithToken() {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            var token = main.token();
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }
    }

    @Test
    public void testNoArguments() {
        var params = new AppParameters();
        var cl = new CommandLine(params);
        assertThrows(MissingParameterException.class, () -> cl.parseArgs(new String[0]));
    }
}
