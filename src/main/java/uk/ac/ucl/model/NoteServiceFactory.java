package uk.ac.ucl.model;

public class NoteServiceFactory {
    private static NoteService noteService;

    public static NoteService getNoteService() {
        if (noteService == null) {
            noteService = new NoteService();
        }
        return noteService;
    }

}
