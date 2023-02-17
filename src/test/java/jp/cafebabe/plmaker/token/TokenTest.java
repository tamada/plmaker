package jp.cafebabe.plmaker.token;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest {
    @Test
    public void testBasic() {
        Token t = new Token("github_token");
        assertEquals("{\"token\":\"github_token\"}", t.toJson());
    }
}
