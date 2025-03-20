package uk.ac.ucl.model;

/**
 * Factory class that provides a singleton instance of the NoteService.
 * This class follows the Factory pattern to centralize the creation of NoteService objects,
 * ensuring that only one instance exists throughout the application lifecycle.
 * It provides a global point of access to the NoteService instance.
 */
public class NoteServiceFactory {
    private static NoteService noteService;

    public static NoteService getNoteService() {
        if (noteService == null) {
            noteService = new NoteService();
        }
        return noteService;
    }

}
