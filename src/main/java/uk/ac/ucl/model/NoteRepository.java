package uk.ac.ucl.model;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing notes and their categories in the application.
 * This interface defines methods for reading, writing, and organizing notes,
 * as well as handling note categories and image uploads.
 * Implementations of this interface handle the actual persistence of notes
 * and their related data.
 * For this coursework, the repository is implemented using JSON files.
 * Might be extended to use a database in the future.
 */
public interface NoteRepository {

    void writeNote(Note note);

    Note loadNoteById(String id);

    void deleteNoteById(String id);

    Set<String> getAllNoteIds();

    Set<String> getNoteIdsByCategory(String categoryName);

    void createNamedCategory(String name);

    void deleteCategory(String name);

    void addNoteToCategory(String noteId, String categoryName);

    void removeNoteFromCategory(String noteId, String categoryName);

    Set<String> getAllCategoryNames();

    List<Note> getNotesByCategory(String categoryName);

    String upLoadImage(InputStream imageInputStream, String fileName);
}
