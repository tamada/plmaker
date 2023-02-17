package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import jp.cafebabe.plmaker.entities.License;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LicenseBuilderTest {
    @Test
    public void testBasic() throws IOException {
        JsonObject object = new Gson()
                .fromJson("{\"key\":\"apache-2.0\",\"name\":\"Apache License 2.0\",\"url\":\"http://choosealicense.com/licenses/apache-2.0/\"}", JsonObject.class);
        var result = new LicenseBuilder().build(object);
        assertNotNull(result);
        assertEquals(new License("apache-2.0", "Apache License 2.0", new URL("http://choosealicense.com/licenses/apache-2.0/")), result);
    }
}
