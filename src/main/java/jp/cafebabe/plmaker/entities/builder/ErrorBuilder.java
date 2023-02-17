package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Error;
import jp.cafebabe.plmaker.entities.Location;

import java.util.ArrayList;
import java.util.List;

class ErrorBuilder implements Builder<List<Error>> {
    @Override
    public List<Error> build(JsonElement object) {
        if(object == null || object.isJsonNull())
            return new ArrayList<>();
        return object.getAsJsonArray().asList()
                .stream().map(element -> element.getAsJsonObject())
                .map(this::toError)
                .toList();
    }

    private Error toError(JsonObject object) {
        String type = object.get("type").getAsString();
        String message = object.get("message").getAsString();
        List<String> paths = object.getAsJsonArray("path")
                .asList().stream().map(e -> e.getAsString()).toList();
        List<Location> locations = buildLocations(object.getAsJsonArray("locations"));
        return new Error(type, message, paths, locations);
    }

    private List<Location> buildLocations(JsonArray array) {
        return array.asList().stream()
                .map(e -> buildLocation(e.getAsJsonObject()))
                .toList();
    }

    private Location buildLocation(JsonObject object) {
        return new Location(object.get("line").getAsInt(), object.get("column").getAsInt());
    }
}
