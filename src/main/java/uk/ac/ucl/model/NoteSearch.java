package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class NoteSearch {
    private NoteRepository noteRepository;

    public NoteSearch(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> searchNotes(String keyword) {
        List<Note> matchingNotes = new ArrayList<>();
        for (String noteId : noteRepository.getAllNoteIndex()) {
            Note note = noteRepository.loadNoteById(noteId);
            if (note != null && (note.getTitle().contains(keyword) || noteContentContainsKeyword(note, keyword))) {
                matchingNotes.add(note);
            }
        }
        return matchingNotes;
    }

    private boolean noteContentContainsKeyword(Note note, String keyword) {
        for (NoteContent content : note.getContents()) {
            if (content.getContent().contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
