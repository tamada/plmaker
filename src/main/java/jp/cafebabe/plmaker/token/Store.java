package jp.cafebabe.plmaker.token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public enum Store {
    HOME,
    CWD,
    NONE;


    public static final Path homePath() {
        return Path.of(System.getProperty("user.home"), ".config", "plmaker", "config.json");
    }

    public static final Path cwdPath() {
        return Path.of(System.getProperty("user.dir"), ".plmaker.json");
    }

    public Path path() {
        return switch(this) {
            case HOME -> homePath();
            case CWD -> cwdPath();
            case NONE -> null;
        };
    }

    public void store(Token token, boolean overwrite) {
        if(path() == null)
            return;
        storeImpl(path(), token, overwrite);
    }

    private void storeImpl(Path path, Token token, boolean overwrite) {
        if(fileNotExistsAndOverwrite(path, overwrite))
            writeJson(path, token);
    }

    private boolean fileNotExistsAndOverwrite(Path p, boolean overwrite) {
        return !Files.exists(p) || overwrite;
    }

    private void writeJson(Path path, Token token) {
        mkdirs(path.getParent());
        try(var out = Files.newBufferedWriter(path)) {
            out.write(token.toJson());
        } catch(IOException e) {
            Logger.getLogger("plmaker").warning(e.getMessage());
        }
    }

    private void mkdirs(Path parent) {
        if(!Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                Logger.getLogger("plmaker").warning(e.getMessage());
            }
        }
    }
}
