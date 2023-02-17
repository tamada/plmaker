package jp.cafebabe.plmaker.entities;

import java.util.List;

public class Error {
    private String type;
    private List<String> paths;
    private List<Location> locations;
    private String message;

    public Error(String type, String message, List<String> paths, List<Location> locations) {
        this.type = type;
        this.message = message;
        this.paths = paths;
        this.locations = locations;
    }

    public void accept(Visitor v) {
        v.visitError(type, message, paths, locations);
    }
}
