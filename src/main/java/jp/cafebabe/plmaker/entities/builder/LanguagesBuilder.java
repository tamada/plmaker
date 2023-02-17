package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;

import java.util.List;

public class LanguagesBuilder implements Builder<List<Language>> {
    private LanguageBuilder builder = new LanguageBuilder();

    @Override
    public List<Language> build(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        var primary = builder.build(object.get("primaryLanguage"));
        return buildLanguages(object.get("languages").getAsJsonObject(), primary);
    }

    private List<Language> buildLanguages(JsonObject object, Language primary) {
        var list = object.getAsJsonArray("nodes").asList()
                .stream().map(e -> builder.build(e))
                .toList();
        setPrimary(list, primary);
        return list;
    }

    private void setPrimary(List<Language> list, Language primary) {
        list.stream().filter(l -> l.isSame(primary))
                .forEach(l -> l.setPrimary(true));
    }
}
