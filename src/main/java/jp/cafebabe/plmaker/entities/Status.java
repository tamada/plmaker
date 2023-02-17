package jp.cafebabe.plmaker.entities;

import java.util.Objects;

public class Status {
    private boolean isArchived = false;
    private boolean isPrivate = false;
    private int diskUsage;

    public Status(boolean isArchived, boolean isPrivate, int diskUsage) {
        this.isArchived = isArchived;
        this.isPrivate = isPrivate;
        this.diskUsage = diskUsage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isArchived, isPrivate, diskUsage);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(other.getClass() != getClass()) return false;
        Status s = (Status) other;
        return isArchived == s.isArchived
                && isPrivate == s.isPrivate
                && diskUsage == s.diskUsage;
    }

    public void accept(Visitor visitor) {
        visitor.visitStatus(isArchived, isPrivate, diskUsage);
    }
}
