package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.logging.Logger;

public interface Builder<T> {
    T build(JsonElement object);

    /**
     * parse the date time string like "2021-12-13T05:22:44Z" to OffsetDateTime.
     */
    default OffsetDateTime getAsDateTime(JsonElement object, String label) {
        return Util.getByMapper(object, label, e -> {
            var instant = Instant.parse(e.getAsString());
            return instant.atOffset(ZoneOffset.ofHours(-9));
        });
    }

    default URL getAsUrl(JsonElement element, String label) {
        return Util.getByMapper(element, label, e -> {
            try {
                return new URL(e.getAsString());
            } catch(IOException ee) {
                Logger.getLogger("plmaker").warning(ee.getMessage());
                return null;
            }
        });
    }

    default String getAsString(JsonElement element, String label) {
        return Util.getByMapper(element, label, e -> e.getAsString());
    }

    final class Util {
        static <K> K getByMapper(JsonElement element, String label, Function<JsonElement, K> mapper) {
            JsonElement target = element.getAsJsonObject().get(label);
            if(target == null || target.isJsonNull())
                return null;
            return mapper.apply(target);
        }
    }
}
