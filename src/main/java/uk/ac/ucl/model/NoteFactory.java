package uk.ac.ucl.model;

import java.util.HashSet;
import java.util.Set;

public class NoteFactory {
    private Set<String> noteIndex = new HashSet<String>();
    private final NoteRepository noteRepository;

    public NoteFactory(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        noteIndex = noteRepository.getAllNoteIndex();
    }

    public Note createNote() {
        String id = generateId();
        Note note = new Note(id, "Untitled");
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
