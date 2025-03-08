package uk.ac.ucl.model;

public interface NoteRepository {

    void writeNote(Note note);

    Note loadNoteById(String id);

    void deleteNoteById(String id);
}
