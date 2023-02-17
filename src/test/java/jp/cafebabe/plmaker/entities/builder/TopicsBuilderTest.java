package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.cafebabe.plmaker.entities.Language;
import jp.cafebabe.plmaker.entities.Topic;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TopicsBuilderTest {
    @Test
    public void testBasic() throws Exception {
        var object = new Gson()
                .fromJson("{\"nodes\":[{\"topic\":{\"name\":\"birthmarks\"},\"url\":\"https://github.com/topics/birthmarks\"},{\"topic\":{\"name\":\"plagiarism-detection\"},\"url\":\"https://github.com/topics/plagiarism-detection\"},{\"topic\":{\"name\":\"software-theft-detection\"},\"url\":\"https://github.com/topics/software-theft-detection\"},{\"topic\":{\"name\":\"java9module\"},\"url\":\"https://github.com/topics/java9module\"},{\"topic\":{\"name\":\"birthmark-toolkit\"},\"url\":\"https://github.com/topics/birthmark-toolkit\"}]}", JsonObject.class);
        var result = new TopicsBuilder().build(object);
        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(createTopic("birthmarks"), result.get(0));
        assertEquals(createTopic("plagiarism-detection"), result.get(1));
        assertEquals(createTopic("software-theft-detection"), result.get(2));
        assertEquals(createTopic("java9module"), result.get(3));
        assertEquals(createTopic("birthmark-toolkit"), result.get(4));
    }

    private Topic createTopic(String label) throws Exception {
        return new Topic(label, new URL("https://github.com/topics/" + label));
    }
}
