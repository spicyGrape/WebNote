package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * Implementation of NoteRepository that uses JSON files for persistent storage.
 * This class manages notes and their categorization by:
 * - Storing individual notes as separate JSON files
 * - Maintaining an index file of all note IDs
 * - Managing a collection of categories and their associated notes
 * - Handling image uploads for note attachments
 * Files are stored in configurable directories specified during initialization.
 */
public class JsonNoteRepository implements NoteRepository {

    private final Map<String, Note> noteCollection;
    private Map<String, Set<String>> categoryCollection; // Map of category names to sets of note IDs

    private final String indexFilePath;
    private final String notesDirectory;
    private final String categoryCollectionFilePath;
    private final String imageDirectory;

    public JsonNoteRepository(String indexFilePath, String notesDirectory, String imageDirectory, String categoryCollectionFilePath) {

        noteCollection = new HashMap<>();
        categoryCollection = new HashMap<>();

        this.indexFilePath = indexFilePath;
        this.notesDirectory = notesDirectory;
        this.categoryCollectionFilePath = categoryCollectionFilePath;
        this.imageDirectory = imageDirectory;

        // Create directories if they do not exist
        if (!Files.exists(new File(notesDirectory).toPath())) {
            new File(notesDirectory).mkdirs();
        }
        if (!Files.exists(new File(imageDirectory).toPath())) {
            new File(imageDirectory).mkdirs();
        }

        loadIdIndex();
        loadCategoryCollection();
    }

    private void loadIdIndex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Set<String> noteIds = objectMapper.readValue(openFile(indexFilePath), new TypeReference<>() {
            });
            // Initialize the notes to null. Lazy load them on demand.
            // Just want the ids at initialization.
            for (String id : noteIds) {
                noteCollection.put(id, null);
            }
        } catch (MismatchedInputException e) {
            // If the index file is empty or not found, leave noteCollection as an empty map.
        } catch (IOException e) {
            e.printStackTrace(); // Carry on with an empty noteCollection
        }
    }

    private void saveIdIndex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(openFile(indexFilePath), noteCollection.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCategoryCollection() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            categoryCollection = objectMapper.readValue(openFile(categoryCollectionFilePath),
                    new TypeReference<>() {
                    });
        } catch (MismatchedInputException e) {
            // If the category collection file is empty or not found, leave categoryCollection as an empty map.
        } catch (IOException e) {
            e.printStackTrace(); // Carry on with an empty categoryCollection
        }
    }

    private void saveCategoryCollection() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(openFile(categoryCollectionFilePath), categoryCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a file at the specified path, creating it if it does not exist.
     *
     * @param path The path to the file.
     * @return The opened file.
     */
    private File openFile(String path) {
        // path must not be null or empty
        if (path == null || path.isEmpty()) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    @Override
    public void createNamedCategory(String name) {
        // Category name must not be All, as this is reserved for the default category that contains all notes.
        // Also, it should not be null or empty.
        if (name == null || name.equals("All") || name.isEmpty()) {
            return;
        }
        Set<String> newCategory = new HashSet<>();
        if (!categoryCollection.containsKey(name)) {
            categoryCollection.put(name, newCategory);
            saveCategoryCollection();
        }
    }

    @Override
    public void addNoteToCategory(String noteId, String categoryName) {
        if (categoryCollection.containsKey(categoryName)) {
            Set<String> noteIds = categoryCollection.get(categoryName);
            if (noteIds != null) {
                noteIds.add(noteId);
            }
            saveCategoryCollection();
        }
    }

    @Override
    public void removeNoteFromCategory(String noteId, String categoryName) {
        if (categoryCollection.containsKey(categoryName)) {
            Set<String> noteIds = categoryCollection.get(categoryName);
            if (noteIds != null) {
                noteIds.remove(noteId);
            }
            saveCategoryCollection();
        }
    }

    @Override
    public void deleteCategory(String name) {
        if (categoryCollection.containsKey(name)) {
            categoryCollection.remove(name);
            saveCategoryCollection();
        }
    }

    private Set<String> getNoteIdsInCategory(String categoryName) {
        if (categoryCollection.containsKey(categoryName)) {
            return categoryCollection.get(categoryName);
        }
        return new HashSet<>();
    }

    public List<Note> getNotesByCategory(String categoryName) {
        Set<String> noteIds = getNoteIdsInCategory(categoryName);
        if (noteIds != null) {
            return noteIds.stream()
                    .map(this::loadNoteById)
                    .toList();
        }
        return new ArrayList<>();
    }

    @Override
    public Set<String> getAllCategoryNames() {
        return categoryCollection.keySet();
    }

    @Override
    public void writeNote(Note note) {
        noteCollection.put(note.getId(), note);
        saveIdIndex();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(openFile(notesDirectory + note.getId() + ".json"), note);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Note loadNoteById(String id) {
        if (noteCollection.containsKey(id)) {
            Note note = noteCollection.get(id);
            if (note == null) {
                note = loadNoteByIdFromFiles(id);
                noteCollection.put(id, note);
            }
            return note;
        }
        return null;
    }

    private Note loadNoteByIdFromFiles(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(openFile(notesDirectory + id + ".json"), Note.class);
        } catch (MismatchedInputException e) {
            try {
                JsonNode jsonNode = objectMapper.readTree(openFile(notesDirectory + id + ".json"));
                String noteTitle = jsonNode.get("title").asText();
                return new Note(id, noteTitle);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteNoteById(String id) {
        Note note = noteCollection.get(id);
        if (note != null) {
            note.deleteAllContents();
        }
        // Remove the note from all categories
        for (String categoryName : categoryCollection.keySet()) {
            Set<String> category = categoryCollection.get(categoryName);
            if (category != null) {
                category.remove(id);
            }
        }
        saveCategoryCollection();
        noteCollection.remove(id);
        saveIdIndex();
        File file = openFile(notesDirectory + id + ".json");
        file.delete();
    }

    @Override
    public Set<String> getAllNoteIds() {
        return noteCollection.keySet();
    }

    @Override
    public String upLoadImage(InputStream imageInputStream, String fileName) {
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        File file = new File(imageDirectory + uniqueFileName);
        try {
            Files.copy(imageInputStream, file.toPath());
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
