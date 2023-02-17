package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Release;

import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.logging.Logger;

class ReleaseBuilder implements Builder<Release> {
    @Override
    public Release build(JsonElement element) {
        OffsetDateTime createdAt = getAsDateTime(element, "createdAt");
        OffsetDateTime publishedAt = getAsDateTime(element, "publishedAt");
        String description = getAsString(element, "description");
        String name = getAsString(element, "name");
        String tagName = getAsString(element, "tagName");
        URL url = getAsUrl(element, "url");
        boolean draft = element.getAsJsonObject().get("isDraft").getAsBoolean();
        return new Release(tagName, name, description, url, createdAt, publishedAt, draft);
    }
}
