package jp.cafebabe.plmaker.token;

import picocli.CommandLine.Command;

@Command(description = "this annotation is for including this class to reflect-config.json")
public class Token {
    private String token;

    Token(String token) {
        this.token = token;
    }

    public String toString() {
        return token;
    }

    public String toJson() {
        return String.format("{\"token\":\"%s\"}", token);
    }
}
