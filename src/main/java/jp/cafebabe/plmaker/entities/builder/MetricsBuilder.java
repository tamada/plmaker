package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Metrics;

class MetricsBuilder implements Builder<Metrics> {
    @Override
    public Metrics build(JsonElement object) {
        return build(object.getAsJsonObject());
    }

    private Metrics build(JsonObject object) {
        int collaborators = object.get("collaborators")
                .getAsJsonObject().get("totalCount").getAsInt();
        int stargazers = object.get("stargazerCount").getAsInt();
        int forkCount = object.get("forkCount").getAsInt();
        int watcherCount = object.get("watchers")
                .getAsJsonObject().get("totalCount").getAsInt();
        return new Metrics(collaborators, stargazers, forkCount, watcherCount);
    }
}
