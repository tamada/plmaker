package jp.cafebabe.plmaker.cli;

import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;

public interface Arguments {
    void validate(CommandSpec spec) throws ParameterException;

    void postprocess();
}
