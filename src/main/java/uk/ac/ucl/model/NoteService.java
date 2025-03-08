package uk.ac.ucl.model;

public class NoteService {
    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public Note getNoteById(String id) {
        return noteRepository.loadNoteById(id);
    }

    public void addNote(Note note) {
        noteRepository.writeNote(note);
    }

    public void updateNote(Note note) {
        noteRepository.writeNote(note);
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteNoteById(id);
    }
}
