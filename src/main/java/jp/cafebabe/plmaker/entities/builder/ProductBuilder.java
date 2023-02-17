package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import jp.cafebabe.plmaker.entities.Product;

public class ProductBuilder implements Builder<Product> {
    @Override
    public Product build(JsonElement element) {
        return new Product(getAsString(element.getAsJsonObject().get("owner"), "login"),
                getAsString(element, "name"));
    }
}
