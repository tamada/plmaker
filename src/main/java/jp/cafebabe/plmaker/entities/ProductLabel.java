package jp.cafebabe.plmaker.entities;

import java.util.ArrayList;
import java.util.List;

public class ProductLabel {
    private Product product;
    private Repository repository;
    private Status status;
    private List<Language> languages = new ArrayList<>();
    private List<Topic> topics = new ArrayList<>();
    private License license;
    private Release release;
    private Metrics metrics;

    private String resultString;

    public ProductLabel(Product product, Repository repository, Status status,
                        License license, List<Language> languages, List<Topic> topics,
                        Release release, Metrics metrics) {
        this.product = product;
        this.repository = repository;
        this.status = status;
        this.license = license;
        this.languages = languages;
        this.topics = topics;
        this.release = release;
        this.metrics = metrics;
    }

    public void accept(Visitor v) {
        product.accept(v);
        repository.accept(v);
        status.accept(v);
        license.accept(v);
        languages.forEach(lang -> lang.accept(v));
        topics.forEach(topic -> topic.accept(v));
        release.accept(v);
        metrics.accept(v);
    }

    public String body() {
        return resultString;
    }
}
