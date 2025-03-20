package uk.ac.ucl.model;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Service class that provides a high-level API for note management operations.
 * This class acts as a facade for the underlying repository and factory components,
 * offering methods for creating, retrieving, updating, and deleting notes,
 * as well as managing categories and performing searches.
 * The service initializes with default file paths for JSON-based persistence,
 * creating the necessary repository, factory, and search components.
 */
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteFactory notefactory;
    private final NoteSearch noteSearch;

    public NoteService() {
        // Default data path
        String indexFilePath = "data" + File.separator + "index.json";
        String categoryCollectionFilePath = "data" + File.separator + "categoryCollection.json";
        String noteFolderPath = "data" + File.separator + "notes" + File.separator;
        String imageFolderPath = "data" + File.separator + "images" + File.separator;
        this.noteRepository = new JsonNoteRepository(indexFilePath, noteFolderPath, imageFolderPath, categoryCollectionFilePath);
        this.notefactory = new NoteFactory(noteRepository);
        this.noteSearch = new NoteSearch(noteRepository);
    }

    public Note createNote() {
        return notefactory.createNote();
    }

    public Note getNoteById(String id) {
        return noteRepository.loadNoteById(id);
    }

    public void addNote(Note note) {
        noteRepository.writeNote(note);
    }

    public void updateNote(Note note) {
        noteRepository.writeNote(note);
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteNoteById(id);
    }

    public String uploadImage(InputStream imageInputStream, String fileName) {
        return noteRepository.upLoadImage(imageInputStream, fileName);

    }

    public List<Note> getAllNotes() {
        return noteSearch.searchNotes("", "All");
    }

    public List<Note> searchNotes(String keyword, String category) {
        return noteSearch.searchNotes(keyword, category);
    }

    public Set<String> getAllCategoryNames() {
        return noteRepository.getAllCategoryNames();
    }

    public List<Note> getNotesByCategory(String categoryName) {
        return noteRepository.getNotesByCategory(categoryName);
    }

    public void createNamedCategory(String name) {
        noteRepository.createNamedCategory(name);
    }

    public void addNoteToCategory(String noteId, String categoryName) {
        noteRepository.addNoteToCategory(noteId, categoryName);
    }

    public void removeNoteFromCategory(String noteId, String categoryName) {
        noteRepository.removeNoteFromCategory(noteId, categoryName);
    }

    public void deleteCategory(String name) {
        noteRepository.deleteCategory(name);
    }

}
