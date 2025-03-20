package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Represents a content element within a Note in the application.
 * Each NoteContent contains the actual content (text, image path, URL, etc.) and a type identifier.
 * The content type determines how the content will be rendered in the user interface.
 * Supported content types include:
 * - "text": Plain text content
 * - "bold_text": Emphasized text content
 * - "image": Path to an image file
 * - "url": Web URL
 * - "html": HTML formatted content
 * This class provides methods to manage the content including deletion of resources
 * when necessary (e.g., deleting image files when content is removed).
 */
public class NoteContent {
    private String content;

    // contentType can be "text", "bold_text", "image", "url" or "html"
    private String contentType;

    @JsonCreator
    public NoteContent(@JsonProperty("content") String content, @JsonProperty("contentType") String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void deleteContent() {
        if (contentType.equals("image")) {
            File file = new File(content);
            if (file.exists()) {
                file.delete();
            }
        }
        this.content = null;
    }
}
