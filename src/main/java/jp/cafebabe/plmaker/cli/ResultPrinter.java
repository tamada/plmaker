package jp.cafebabe.plmaker.cli;

import jp.cafebabe.plmaker.entities.Location;
import jp.cafebabe.plmaker.entities.Visitor;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

public class ResultPrinter implements Visitor, Closeable {
    private PrintWriter out;
    private boolean first = true;
    private boolean firstLanguage = true;
    private boolean firstTopic = true;

    public ResultPrinter(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void visit(int statusCode) {
        out.print(first? "[": ",");
        firstLanguage = true;
        firstTopic = true;
        first = false;
    }

    @Override
    public void visitHeader(String key, List<String> value) {
    }

    @Override
    public void visitProduct(String owner, String repository) {
        out.printf("{\"owner\":\"%s\",\"repository\":\"%s\"", owner, repository);
    }

    @Override
    public void visitInfo(String description, URL url, URL homepageUrl, OffsetDateTime createdAt) {
        out.printf(",\"description\":\"%s\",\"url\":\"%s\",\"homepageUrl\":\"%s\",\"createdAt\":\"%s\"",
                description, url, homepageUrl, createdAt);
    }

    @Override
    public void visitLastModifiedAt(OffsetDateTime lastModifiedAt) {
        out.printf(",\"lastModifiedAt\":\"%s\"", lastModifiedAt);
    }

    @Override
    public void visitStatus(boolean isArchived, boolean isPrivate, int diskUsage) {
        out.printf(",\"isArchived\":%s,\"isPrivate\":%s,\"diskUsage\":%d",
                isArchived, isPrivate, diskUsage);
    }

    @Override
    public void visitLicense(String key, String name, URL url) {
        out.printf(",\"license\":{\"key\":\"%s\",\"name\":\"%s\",\"url\":\"%s\"}", key, name, url);
    }

    @Override
    public void visitLanguage(String language, String color, boolean primary) {
        out.print(",");
        if(firstLanguage)
            out.print("\"languages\":[");
        out.printf("{\"language\":\"%s\",\"color\":\"%s\",\"primary\":%s}", language, color, primary);
        firstLanguage = false;
    }

    @Override
    public void visitTopic(String name, URL url) {
        out.printf(firstTopic? "],\"topics\":[": ",");
        out.printf("{\"name\":\"%s\",\"url\":\"%s\"}", name, url);
        firstTopic = false;
    }

    @Override
    public void visitRelease(String tagName, String name, String description, URL url, OffsetDateTime createdAt, OffsetDateTime publishedAt, boolean draft) {
        out.printf("],\"release\":{\"tagName\":\"%s\",\"name\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"createdAt\":\"%s\",\"publishedAt\":\"%s\",\"draft\":%s}",
                tagName, name, description, url, createdAt, publishedAt, draft);
    }

    @Override
    public void visitCollaboratorCount(int collaboratorCount) {
        out.printf(",\"collaborators\":%d", collaboratorCount);
    }

    @Override
    public void visitStargazerCount(int stargazerCount) {
        out.printf(",\"stargazers\":%d", stargazerCount);
    }

    @Override
    public void visitForkCount(int forkCount) {
        out.printf(",\"forks\":%d", forkCount);
    }

    @Override
    public void visitWatcherCount(int watcherCount) {
        out.printf(",\"watchers\":%d", watcherCount);
    }

    @Override
    public void visitError(String type, String message, List<String> path, List<Location> location) {

    }

    @Override
    public void visitEnd() {
        out.print("}");
    }

    @Override
    public void close() {
        out.println("]");
        out.close();
    }
}
