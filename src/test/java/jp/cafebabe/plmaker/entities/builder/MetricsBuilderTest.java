package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MetricsBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"collaborators\":{\"totalCount\":3},\"stargazerCount\":4,\"forkCount\":5,\"watchers\":{\"totalCount\":6}}", JsonObject.class);
        var result = new MetricsBuilder().build(object);
        assertNotNull(result);
        assertEquals(3, result.collaboratorCount());
        assertEquals(4, result.stargazerCount());
        assertEquals(5, result.forkCount());
        assertEquals(6, result.watcherCount());
    }
}
