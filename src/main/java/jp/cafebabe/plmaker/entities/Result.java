package jp.cafebabe.plmaker.entities;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Result {
    private int status;
    private List<Error> errors;
    private ProductLabel label;
    private String resultJson;

    private Map<String, List<String>> headers;

    public Result(int status, String result, ProductLabel label, List<Error> errors, Map<String, List<String>> headers) {
        this.status = status;
        this.errors = errors;
        this.label = label;
        this.resultJson = result;
        this.headers = headers;
    }

    public void accept(Visitor visitor) {
        visitor.visit(status);
        headers.forEach((key, value) -> visitor.visitHeader(key, value));
        label.accept(visitor);
        errors.forEach(e -> e.accept(visitor));
        visitor.visitEnd();
    }

    public int statusCode() {
        return status;
    }

    public String body() {
        return resultJson;
    }
}
