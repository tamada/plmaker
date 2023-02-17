package jp.cafebabe.plmaker.entities;

import java.util.Objects;

public class Language {
    private String language;
    private String color;
    private boolean primary;

    public Language(String language, String color, boolean primary) {
        this(language, color);
        setPrimary(primary);
    }

    public Language(String language, String color) {
        this.language = language;
        this.color = color;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isSame(Language other) {
        return other != null
                && Objects.equals(language, other.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, color, primary);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(other.getClass() != getClass()) return false;
        Language lang = (Language) other;
        return Objects.equals(language, lang.language)
                && Objects.equals(color, lang.color)
                && primary == lang.primary;
    }

    @Override
    public String toString() {
        return String.format("language[%s, %s%s]", language, color, primary? " (primary)":"");
    }

    public void accept(Visitor visitor) {
        visitor.visitLanguage(language, color, primary);
    }
}
