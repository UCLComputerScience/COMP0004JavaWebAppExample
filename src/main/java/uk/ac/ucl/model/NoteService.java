package uk.ac.ucl.model;

import java.util.List;

public class NoteService {
    private NoteRepository noteRepository;
    private NoteFactory notefactory;
    private NoteSearch noteSearch;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        this.notefactory = new NoteFactory(noteRepository);
        this.noteSearch = new NoteSearch(noteRepository);
    }

    public NoteService(String dataPath) {
        String indexFilePath = dataPath + "/index.json";
        String noteFolderPath = dataPath + "/notes";
        this.noteRepository = new JsonNoteRepository(indexFilePath, noteFolderPath);
        this.notefactory = new NoteFactory(noteRepository);
        this.noteSearch = new NoteSearch(noteRepository);
    }

    public Note createNote() {
        return notefactory.createNote();
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

    public List<Note> searchNotes(String keyword) {
        return noteSearch.searchNotes(keyword);
    }
}
