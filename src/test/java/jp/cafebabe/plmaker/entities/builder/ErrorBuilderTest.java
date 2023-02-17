package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorBuilderTest {
    @Test
    public void testBasic() {
        JsonElement object = new Gson()
                .fromJson("[{\"type\":\"NOT_FOUND\",\"path\":[\"repository\"],\"locations\":[{\"line\":1,\"column\":44}],\"message\":\"Could not resolve to a Repository with the name 'tamada/8rules'.\"}]", JsonElement.class);
        var result = new ErrorBuilder().build(object);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testEmptyEntry() {
        JsonElement element = new Gson().fromJson("", JsonElement.class);
        var result = new ErrorBuilder().build(element);
        assertEquals(0, result.size());
    }

    @Test
    public void testNull() {
        JsonElement element = new Gson().fromJson("null", JsonElement.class);
        var result = new ErrorBuilder().build(element);
        assertEquals(0, result.size());
    }
}
