package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import jp.cafebabe.plmaker.entities.License;

class LicenseBuilder implements Builder<License> {
    @Override
    public License build(JsonElement element) {
        return new License(getAsString(element, "key"),
                getAsString(element, "name"),
                getAsUrl(element, "url"));
    }
}
