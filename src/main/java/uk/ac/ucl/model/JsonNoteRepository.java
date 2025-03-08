package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonNoteRepository implements NoteRepository {
    private Map<String, Note> notes;
    private final String indexFilePath;
    private final String notesDirectory;

    public JsonNoteRepository(String indexFilePath, String notesDirectory) {
        notes = new HashMap<>();
        this.indexFilePath = indexFilePath;
        this.notesDirectory = notesDirectory;
        loadIdIndex();
    }

    private void saveIdIndex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(openFile(indexFilePath), notes.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File openFile(String path) {
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

    private void loadIdIndex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Set<String> noteIds = objectMapper.readValue(openFile(indexFilePath), new TypeReference<Set<String>>() {
            });
            // Initialize the notes to null. Lazy load them on demand.
            // Just want the ids at initialization.
            for (String id : noteIds) {
                notes.put(id, null);
            }
        } catch (MismatchedInputException e) {
            // If the file is empty, just return.
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void writeNote(Note note) {
        notes.put(note.getId(), note);
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
        if (notes.containsKey(id)) {
            Note note = notes.get(id);
            if (note == null) {
                note = loadNoteByIdFromFiles(id);
                notes.put(id, note);
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
        notes.remove(id);
        saveIdIndex();
        File file = openFile(notesDirectory + id + ".json");
        file.delete();
    }

    @Override
    public Set<String> getAllNoteIndex() {
        return notes.keySet();
    }

}
