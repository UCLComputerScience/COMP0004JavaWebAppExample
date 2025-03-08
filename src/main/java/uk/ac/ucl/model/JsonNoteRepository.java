package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            objectMapper.writeValue(new File("data/noteIds.json"), notes.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIdIndex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Set<String> noteIds = objectMapper.readValue(new File(indexFilePath), new TypeReference<Set<String>>() {
            });
            // Initialize the notes to null. Lazy load them on demand.
            // Just want the ids at initialization.
            for (String id : noteIds) {
                notes.put(id, null);
            }
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
            objectMapper.writeValue(new File(notesDirectory + note.getId() + ".json"), note);
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
            return objectMapper.readValue(new File(notesDirectory + id + ".json"), Note.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteNoteById(String id) {
        notes.remove(id);
        saveIdIndex();
        File file = new File(notesDirectory + id + ".json");
        file.delete();
    }

}
