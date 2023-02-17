package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryBuilderTest {
    @Test
    public void testBasic() {
        JsonElement object = new Gson()
                .fromJson("{\"description\":\"Java birthmark toolkit, detecting the software theft by native characteristics of the programs.\",\"homepageUrl\":\"https://tamada.github.io/pochi\",\"url\":\"https://github.com/tamada/pochi\",\"createdAt\":\"2017-02-22T07:18:34Z\",\"lastModifiedAt\":\"2022-11-16T01:23:49Z\"}", JsonElement.class);
        var result = new RepositoryBuilder().build(object);
        assertNotNull(result);
        assertEquals("Java birthmark toolkit, detecting the software theft by native characteristics of the programs.", result.description());
    }

    @Test
    public void testNull() {
        JsonElement object = new Gson().fromJson("null", JsonElement.class);
        var result = new RepositoryBuilder().build(object);
        assertNull(result);
    }
}
