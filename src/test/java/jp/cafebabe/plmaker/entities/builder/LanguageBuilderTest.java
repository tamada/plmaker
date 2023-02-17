package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanguageBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"name\":\"Java\",\"color\":\"#b07219\"}", JsonObject.class);
        var result = new LanguageBuilder().build(object);
        assertNotNull(result);
        assertEquals(new Language("Java", "#b07219"), result);
    }
}
