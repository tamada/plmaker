package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Status;

class StatusBuilder implements Builder<Status> {
    @Override
    public Status build(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        return new Status(object.get("isArchived").getAsBoolean(),
                object.get("isPrivate").getAsBoolean(),
                object.get("diskUsage").getAsInt());
    }
}
