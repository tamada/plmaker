package jp.cafebabe.plmaker.token;

import com.google.gson.Gson;
import jp.cafebabe.plmaker.cli.Main;
import jp.cafebabe.plmaker.cli.MainTest;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreTest {
    @Test
    public void testNone() {
        assertNull(Store.NONE.path());
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Home {
        private static Path homeBase;
        private static Path cwdBase;

        @BeforeAll
        public static void setSystemProperty() throws IOException {
            var props = System.getProperties();
            homeBase = Files.createTempDirectory("home");
            cwdBase = Files.createTempDirectory("cwd");
            props.setProperty("user.home", homeBase.toString());
            props.setProperty("user.dir", cwdBase.toString());
        }

        @AfterAll
        public static void deleteDirectory() {
            purgeDirectory(homeBase.toFile());
            purgeDirectory(cwdBase.toFile());
        }

        @Test
        @Order(1)
        public void testStoreViaHome() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            main.postprocess();
            Store.HOME.store(main.token(), false);
            Token token = readJson(Store.HOME.path());
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }

        @Test
        @Order(2)
        public void testStoreNoOverwrite() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token2".split(" "));
            main.validate();
            main.postprocess();
            Store.HOME.store(main.token(), false);
            Token token = readJson(Store.HOME.path());
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }

        @Test
        @Order(3)
        public void testStoreOverwrite() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token3".split(" "));
            main.validate();
            main.postprocess();
            Store.HOME.store(main.token(), true);
            Token token = readJson(Store.HOME.path());
            assertEquals("{\"token\":\"cli_token3\"}", token.toJson());
        }
    }


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class Cwd {
        private static Path homeBase;
        private static Path cwdBase;

        @BeforeAll
        public static void setSystemProperty() throws IOException {
            var props = System.getProperties();
            homeBase = Files.createTempDirectory("home");
            cwdBase = Files.createTempDirectory("cwd");
            props.setProperty("user.home", homeBase.toString());
            props.setProperty("user.dir", cwdBase.toString());
        }

        @AfterAll
        public static void deleteDirectory() {
            purgeDirectory(homeBase.toFile());
            purgeDirectory(cwdBase.toFile());
        }

        @Test
        @Order(1)
        public void testStoreViaHome() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token".split(" "));
            main.validate();
            main.postprocess();
            Store.CWD.store(main.token(), false);
            Token token = readJson(Store.CWD.path());
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }

        @Test
        @Order(2)
        public void testStoreNoOverwrite() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token2".split(" "));
            main.validate();
            main.postprocess();
            Store.CWD.store(main.token(), false);
            Token token = readJson(Store.CWD.path());
            assertEquals("{\"token\":\"cli_token\"}", token.toJson());
        }

        @Test
        @Order(3)
        public void testStoreOverwrite() throws IOException {
            var main = new Main();
            var cl = new CommandLine(main);
            cl.parseArgs("tamada/plmaker -t cli_token3".split(" "));
            main.validate();
            main.postprocess();
            Store.CWD.store(main.token(), true);
            Token token = readJson(Store.CWD.path());
            assertEquals("{\"token\":\"cli_token3\"}", token.toJson());
        }
    }

    private Token readJson(Path path) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(Files.newBufferedReader(path), Token.class);
    }

    private static void purgeDirectory(File file) {
        for(File entry: file.listFiles()) {
            if(entry.isDirectory())
                purgeDirectory(entry);
            else
                file.delete();
        }
        file.delete();
    }
}
