package uk.ac.ucl.model;

import java.util.Set;

public interface NoteRepository {

    void writeNote(Note note);

    Note loadNoteById(String id);

    void deleteNoteById(String id);

    Set<String> getAllNoteIndex();
}
