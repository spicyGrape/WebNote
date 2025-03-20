package uk.ac.ucl.model;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface NoteRepository {

    void writeNote(Note note);

    Note loadNoteById(String id);

    void deleteNoteById(String id);

    Set<String> getAllNoteIds();

    void createNamedCategory(String name);

    void addNoteToCategory(String noteId, String categoryName);

    void removeNoteFromCategory(String noteId, String categoryName);

    Set<String> getAllCategoryNames();

    List<Note> getNotesByCategory(String categoryName);

    String upLoadImage(InputStream imageInputStream, String fileName);
}
