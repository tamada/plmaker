package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Topic;

import java.net.URL;
import java.util.List;

class TopicsBuilder implements Builder<List<Topic>> {
    @Override
    public List<Topic> build(JsonElement object) {
        return object.getAsJsonObject().getAsJsonArray("nodes")
                .asList().stream().map(e -> e.getAsJsonObject())
                .map(o -> buildTopic(o))
                .toList();
    }

    private Topic buildTopic(JsonObject object) {
        String topic = getAsString(object.get("topic"), "name");
        URL url = getAsUrl(object, "url");
        return new Topic(topic, url);
    }
}
