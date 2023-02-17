package jp.cafebabe.plmaker.cli;

import picocli.CommandLine.IVersionProvider;

import java.util.Properties;

public class PLMaker implements IVersionProvider {
    @Override
    public String[] getVersion() throws Exception {
        var props = new Properties();
        try(var in = PLMaker.class.getResourceAsStream("/resources/plmaker.properties")) {
            props.load(in);
        }
        return new String[] {
                props.getProperty("plmaker.version"),
        };
    }
}
