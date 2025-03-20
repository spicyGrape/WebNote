package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a note in the application with a unique identifier, title and various content elements.
 * A note can contain multiple content items of different types (text, images, etc.) stored in a list.
 */
public class Note {
    private final String id;
    private String title;
    private List<NoteContent> contents;

    @JsonCreator
    public Note(@JsonProperty("id") String id, @JsonProperty("title") String title, @JsonProperty("contents") List<NoteContent> contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Note(String id, String title) {
        this.id = id;
        this.title = title;
        this.contents = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(List<NoteContent> contents) {
        this.contents = contents;
    }

    public List<NoteContent> getContents() {
        return contents;
    }

    public void appendImage(String content, String contentType) {
        NoteContent newContent = new NoteContent(content, contentType);
        contents.add(newContent);
    }

    /**
     * Converts all contents of the note into a single text summary.
     * Text content is included as is (with HTML breaks replaced by spaces),
     * while non-text content (like images) is represented by a descriptive placeholder.
     *
     * @return A String containing the textual summary of all note contents
     */
    public String summarizeIntoText() {
        StringBuilder summary = new StringBuilder();
        for (NoteContent content : contents) {
            if (content.getContentType().equals("text")) {
                summary.append(content.getContent().replaceAll("<br>", " "));
            } else {
                // For non-text content, we can append a placeholder or description
                String description = "[A piece of " + content.getContentType() + " content] ";
                summary.append(description);
            }
        }
        return summary.toString();
    }

    public void deleteAllContents() {
        for (NoteContent content : contents) {
            content.deleteContent(); // To clean up for images uploaded
        }
        contents.clear();
    }

}
