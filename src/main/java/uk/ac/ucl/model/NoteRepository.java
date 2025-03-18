package uk.ac.ucl.model;

import java.io.InputStream;
import java.util.Set;

public interface NoteRepository {

    void writeNote(Note note);

    Note loadNoteById(String id);

    void deleteNoteById(String id);

    Set<String> getAllNoteIndex();

    String upLoadImage(InputStream imageInputStream, String fileName);
}
