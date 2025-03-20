package uk.ac.ucl.model;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class NoteService {
    private NoteRepository noteRepository;
    private NoteFactory notefactory;
    private NoteSearch noteSearch;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        this.notefactory = new NoteFactory(noteRepository);
        this.noteSearch = new NoteSearch(noteRepository);
    }

    public NoteService(String dataPath) {
        String indexFilePath = dataPath + "/index.json";
        String categoryCollectionFilePath = dataPath + File.separator + "/categoryCollection.json";
        String noteFolderPath = dataPath + "/notes/";
        String imageFolderPath = dataPath + "/images/";
        this.noteRepository = new JsonNoteRepository(indexFilePath, noteFolderPath, imageFolderPath, categoryCollectionFilePath);
        this.notefactory = new NoteFactory(noteRepository);
        this.noteSearch = new NoteSearch(noteRepository);
    }

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
        return noteSearch.searchNotes("");
    }

    public List<Note> searchNotes(String keyword) {
        return noteSearch.searchNotes(keyword);
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

}
