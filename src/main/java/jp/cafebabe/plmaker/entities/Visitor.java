package jp.cafebabe.plmaker.entities;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * plmaker calls the following <code>visit</code> methods in the following order.
 * <ul>
 *     <li><code>visit</code></li>
 *     <li><code>visitHeader</code> (several times)</li>
 *     <li><code>visitProduct</code></li>
 *     <li><code>visitInfo</code></li>
 *     <li><code>visitLastModified</code></li>
 *     <li><code>visitStatus</code></li>
 *     <li><code>visitLicense</code></li>
 *     <li><code>visitLanguage</code> (several times)</li>
 *     <li><code>visitTopic</code> (several times)</li>
 *     <li><code>visitRelease</code></li>
 *     <li><code>visitCollaboratorCount</code></li>
 *     <li><code>visitStargazerCount</code></li>
 *     <li><code>visitForkCount</code></li>
 *     <li><code>visitWatcherCount</code></li>
 *     <li><code>visitError</code> (several times or zero)</li>
 *     <li><code>visitEnd</code></li>
 * </ul>
 */
public interface Visitor {
    void visit(int statusCode);
    void visitHeader(String key, List<String> value);
    void visitProduct(String owner, String repository);

    void visitInfo(String description, URL url, URL homepageUrl, OffsetDateTime createdAt);

    void visitLastModifiedAt(OffsetDateTime lastModifiedAt);

    void visitStatus(boolean isArchived, boolean isPrivate, int diskUsage);

    void visitLicense(String key, String name, URL url);

    void visitLanguage(String language, String color, boolean primary);

    void visitTopic(String name, URL url);

    void visitRelease(String tagName, String name, String description, URL url,
                      OffsetDateTime createdAt, OffsetDateTime publishedAt, boolean draft);

    void visitCollaboratorCount(int collaboratorCount);

    void visitStargazerCount(int stargazerCount);

    void visitForkCount(int forkCount);

    void visitWatcherCount(int watcherCount);

    void visitError(String type, String message, List<String> path, List<Location> location);

    void visitEnd();
}
