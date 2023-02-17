package jp.cafebabe.plmaker.entities.builder;

import com.google.gson.JsonElement;
import jp.cafebabe.plmaker.entities.ProductLabel;

class ProductLabelBuilder implements Builder<ProductLabel> {
    @Override
    public ProductLabel build(JsonElement element) {
        return new ProductLabel(new ProductBuilder().build(element),
                new RepositoryBuilder().build(element),
                new StatusBuilder().build(element),
                new LicenseBuilder().build(element.getAsJsonObject().get("licenseInfo")),
                new LanguagesBuilder().build(element),
                new TopicsBuilder().build(element.getAsJsonObject().get("repositoryTopics")),
                new ReleaseBuilder().build(element.getAsJsonObject().get("latestRelease")),
                new MetricsBuilder().build(element));
    }
}
