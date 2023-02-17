package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import jp.cafebabe.plmaker.entities.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StatusBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"isArchived\":false,\"isPrivate\":false,\"visibility\":\"PUBLIC\",\"diskUsage\":34091}", JsonObject.class);
        var result = new StatusBuilder().build(object);
        assertNotNull(result);
        assertEquals(new Status(false, false, 34091), result);
    }
}
