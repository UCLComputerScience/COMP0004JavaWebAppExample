import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.JsonNoteRepository;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;

import java.util.List;

public class NoteServiceTest {
    @Test
    public void testNoteServiceCreation() {
        NoteService noteService = new NoteService(new JsonNoteRepository("temp/testIndex.json", "temp/notes/"));
        assert noteService != null;
    }

    @Test
    public void testNoteServiceAddNote() {
        NoteService noteService = new NoteService(new JsonNoteRepository("temp/testIndex.json", "temp/notes/"));
        noteService.addNote(new Note("2", "Test Note"));
        noteService.addNote(new Note("3", "Test Note"));
        Note note2 = noteService.getNoteById("2");
        assert note2 != null;
        assert note2.getId().equals("2");
        assert note2.getTitle().equals("Test Note");
    }

    @Test
    public void testNoteServiceUpdateNote() {
        NoteService noteService = new NoteService(new JsonNoteRepository("temp/testIndex.json", "temp/notes/"));
        Note note3 = noteService.getNoteById("3");
        note3.setTitle("Updated Test Note");
        noteService.updateNote(note3);
        Note updatedNote3 = noteService.getNoteById("3");
        assert updatedNote3 != null;
        assert updatedNote3.getTitle().equals("Updated Test Note");
    }

    @Test
    public void testNoteServiceGetNote() {
        NoteService noteService = new NoteService(new JsonNoteRepository("temp/testIndex.json", "temp/notes/"));
        Note note2 = noteService.getNoteById("2");
        assert note2 != null;
        assert note2.getId().equals("2");
        assert !note2.getTitle().isEmpty();
    }

    @Test
    public void testNoteServiceSearchNotes() {
        NoteService noteService = new NoteService(new JsonNoteRepository("temp/testIndex.json", "temp/notes/"));
        noteService.addNote(new Note("4", "Search Test Note"));
        List<Note> searchResults = noteService.searchNotes("Search Test Note");
        assert searchResults.size() == 1;
        assert searchResults.get(0).getTitle().equals("Search Test Note");
    }
}
