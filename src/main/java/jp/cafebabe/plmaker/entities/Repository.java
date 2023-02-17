package jp.cafebabe.plmaker.entities;

import java.net.URL;
import java.time.OffsetDateTime;

public class Repository {
    private URL homepageUrl;
    private URL url;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastModifiedAt;

    public Repository(String description, URL url, URL homepageUrl,
                      OffsetDateTime createdAt, OffsetDateTime lastModifiedAt) {
        this.description = description;
        this.url = url;
        this.homepageUrl = homepageUrl;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public String description() {
        return description;
    }

    public void accept(Visitor v) {
        v.visitInfo(description, url, homepageUrl, createdAt);
        v.visitLastModifiedAt(lastModifiedAt);
    }
}
