package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.ProductLabel;
import jp.cafebabe.plmaker.entities.Repository;

class RepositoryBuilder implements Builder<Repository> {
    @Override
    public Repository build(JsonElement element) {
        if(element.isJsonNull())
            return null;
        return new Repository(getAsString(element, "description"),
                getAsUrl(element, "url"),
                getAsUrl(element, "homepageUrl"),
                getAsDateTime(element, "createdAt"),
                getAsDateTime(element, "lastModifiedAt"));
    }
}
