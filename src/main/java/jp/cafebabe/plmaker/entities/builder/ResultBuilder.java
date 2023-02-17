package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Error;
import jp.cafebabe.plmaker.entities.ProductLabel;
import jp.cafebabe.plmaker.entities.Result;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ResultBuilder {
    public Result build(HttpResponse<String> response) {
        JsonObject object = new Gson().fromJson(response.body(), JsonObject.class);
        ProductLabel label = new ProductLabelBuilder().build(object.getAsJsonObject("data")
                .getAsJsonObject("repository"));
        List<Error> errors = new ErrorBuilder().build(object.getAsJsonObject("errors"));
        return new Result(response.statusCode(), response.body(), label, errors, mapHeader(response.headers()));
    }

    private Map<String, List<String>> mapHeader(HttpHeaders headers) {
        return headers.map();
    }
}
