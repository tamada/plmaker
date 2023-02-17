package jp.cafebabe.plmaker.entities;

public class Location {
    private int line;
    private int column;

    public Location(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }
}
