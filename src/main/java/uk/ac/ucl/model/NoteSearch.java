package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for searching notes in the repository based on keywords.
 * Provides functionality to search through note titles and contents,
 * returning matching notes that contain the specified keyword.
 */
public class NoteSearch {
    private final NoteRepository noteRepository;

    public NoteSearch(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> searchNotes(String keyword, String category) {
        keyword = keyword.toLowerCase();
        List<Note> matchingNotes = new ArrayList<>();
        Iterable<String> noteIdCollection;
        if (category == null || category.isEmpty() || category.equals("All")) {
            noteIdCollection = noteRepository.getAllNoteIds();
        } else {
            noteIdCollection = noteRepository.getNoteIdsByCategory(category);
        }
        for (String noteId : noteIdCollection) {
            Note note = noteRepository.loadNoteById(noteId);
            if (note != null && (note.getTitle().toLowerCase().contains(keyword) || noteContentContainsKeyword(note, keyword))) {
                matchingNotes.add(note);
            }
        }
        return matchingNotes;
    }

    private boolean noteContentContainsKeyword(Note note, String keyword) {
        keyword = keyword.toLowerCase();
        for (NoteContent content : note.getContents()) {
            if (content.getContent().toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
