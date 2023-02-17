package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import jp.cafebabe.plmaker.entities.Language;

class LanguageBuilder implements Builder<Language> {
    @Override
    public Language build(JsonElement element) {
        var object = element.getAsJsonObject();
        return new Language(object.get("name").getAsString(),
                object.get("color").getAsString());
    }
}
