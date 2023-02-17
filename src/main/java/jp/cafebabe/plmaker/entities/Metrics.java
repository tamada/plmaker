package jp.cafebabe.plmaker.entities;

public class Metrics {
    private int collaboratorCount;
    private int stargazerCount;
    private int forkCount;
    private int watcherCount;

    public Metrics(int collaboratorCount, int stargazerCount, int forkCount, int watcherCount) {
        this.collaboratorCount = collaboratorCount;
        this.stargazerCount = stargazerCount;
        this.forkCount = forkCount;
        this.watcherCount = watcherCount;
    }

    public int collaboratorCount() {
        return collaboratorCount;
    }

    public int stargazerCount() {
        return stargazerCount;
    }

    public int forkCount() {
        return forkCount;
    }

    public int watcherCount() {
        return watcherCount;
    }

    public void accept(Visitor v) {
        v.visitCollaboratorCount(collaboratorCount);
        v.visitStargazerCount(stargazerCount);
        v.visitForkCount(forkCount);
        v.visitWatcherCount(watcherCount);
    }
}
