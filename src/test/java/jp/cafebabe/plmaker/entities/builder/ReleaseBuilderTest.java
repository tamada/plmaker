package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReleaseBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"createdAt\":\"2021-12-13T05:22:44Z\",\"description\":null,\"isDraft\":false,\"name\":\"Release v2.6.0\",\"publishedAt\":\"2021-12-13T05:24:16Z\",\"tagName\":\"v2.6.0\",\"url\":\"https://github.com/tamada/pochi/releases/tag/v2.6.0\"}", JsonObject.class);
        var result = new ReleaseBuilder().build(object);
        assertNotNull(result);
    }
}
