package jp.cafebabe.plmaker.entities;

import java.net.URL;
import java.util.Objects;

public class License {
    private String name;
    private String key;
    private URL url;

    public License(String key, String name, URL url) {
        this.key = key;
        this.name = name;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name, url);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(other.getClass() != getClass()) return false;
        License license = (License) other;
        return Objects.equals(key, license.key)
                && Objects.equals(name, license.name)
                && Objects.equals(url, license.url);
    }

    public void accept(Visitor visitor) {
        visitor.visitLicense(key, name, url);
    }
}
