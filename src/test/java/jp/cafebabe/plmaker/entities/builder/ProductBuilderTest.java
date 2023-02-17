package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import jp.cafebabe.plmaker.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductBuilderTest {
    @Test
    public void testBasic() {
        JsonObject object = new Gson()
                .fromJson("{\"name\":\"pochi\",\"owner\":{\"login\":\"tamada\"}}", JsonObject.class);
        var result = new ProductBuilder().build(object);
        assertNotNull(result);
        assertEquals(new Product("tamada", "pochi"), result);
    }
}
