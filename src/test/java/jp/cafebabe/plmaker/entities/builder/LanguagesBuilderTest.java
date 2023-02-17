package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanguagesBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"primaryLanguage\":{\"name\":\"Java\",\"color\":\"#b07219\"},\"languages\":{\"nodes\":[{\"name\":\"Java\",\"color\":\"#b07219\"},{\"name\":\"HTML\",\"color\":\"#e34c26\"},{\"name\":\"JavaScript\",\"color\":\"#f1e05a\"},{\"name\":\"CSS\",\"color\":\"#563d7c\"},{\"name\":\"Dockerfile\",\"color\":\"#384d54\"}]}}", JsonObject.class);
        var result = new LanguagesBuilder().build(object);
        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(new Language("Java", "#b07219", true), result.get(0));
        assertEquals(new Language("HTML", "#e34c26"), result.get(1));
        assertEquals(new Language("JavaScript", "#f1e05a"), result.get(2));
        assertEquals(new Language("CSS", "#563d7c"), result.get(3));
        assertEquals(new Language("Dockerfile", "#384d54"), result.get(4));
    }
}
