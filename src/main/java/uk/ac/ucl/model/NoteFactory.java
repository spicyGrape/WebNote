package uk.ac.ucl.model;

import java.util.Set;

/**
 * Factory class responsible for creating new Note objects with unique identifiers.
 * This class interacts with a NoteRepository to check existing note IDs and
 * persist newly created notes. It ensures that each note has a unique ID by
 * generating timestamp-based identifiers and checking them against the existing
 * note index.
 */
public class NoteFactory {
    private final Set<String> noteIndex;
    private final NoteRepository noteRepository;

    public NoteFactory(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        noteIndex = noteRepository.getAllNoteIds();
    }

    public Note createNote() {
        String id = generateId();
        Note note = new Note(id, "Untitled"); // Default title
        noteRepository.writeNote(note);
        return note;
    }

    private String generateId() {
        long id = System.currentTimeMillis();
        while (noteIndex.contains(String.valueOf(id))) {
            id++;
        }
        return String.valueOf(id);
    }

}
