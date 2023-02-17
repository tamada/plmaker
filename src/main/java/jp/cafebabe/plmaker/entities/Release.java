package jp.cafebabe.plmaker.entities;

import java.net.URL;
import java.time.OffsetDateTime;

public class Release {
    private OffsetDateTime createdAt;
    private OffsetDateTime publishedAt;
    private String description;
    private String name;
    private String tagName;
    private URL url;
    private boolean draft;

    public Release(String tagName, String name, String description, URL url,
                   OffsetDateTime createdAt, OffsetDateTime publishedAt, boolean draft) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.url = url;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
        this.draft = draft;
    }

    public void accept(Visitor v) {
        v.visitRelease(tagName, name, description, url, createdAt, publishedAt, draft);
    }
}
