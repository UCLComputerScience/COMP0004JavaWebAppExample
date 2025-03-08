package uk.ac.ucl.model;

import java.util.Map;

public interface NoteRepository {

    void saveNote(Note note);

    Note getNoteById(String id);

    Map<String, Note> getAllNotes();

}
