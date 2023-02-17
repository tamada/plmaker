package jp.cafebabe.plmaker.entities;

import java.net.URL;
import java.util.Objects;

public class Topic {
    private String name;
    private URL url;

    public Topic(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(other.getClass() != getClass()) return false;
        Topic topic = (Topic)other;
        return Objects.equals(name, topic.name)
                && Objects.equals(url, topic.url);
    }

    public void accept(Visitor v) {
        v.visitTopic(name, url);
    }
}
