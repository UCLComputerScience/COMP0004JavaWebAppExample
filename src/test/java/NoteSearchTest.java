import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.JsonNoteRepository;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteContent;
import uk.ac.ucl.model.NoteSearch;

import java.util.List;

public class NoteSearchTest {
    String testIndexPath = "temp/testIndex.json";
    String testNotesDirectory = "temp/notes/";

    @Test
    public void testSearchNotesByTitle() {
        JsonNoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        NoteSearch noteSearch = new NoteSearch(noteRepository);

        Note note1 = new Note("1", "Test Note 1");
        note1.addContent(new NoteContent("This is the content of test note 1"));
        noteRepository.writeNote(note1);

        Note note2 = new Note("2", "Another Test Note");
        note2.addContent(new NoteContent("This is the content of another test note"));
        noteRepository.writeNote(note2);

        List<Note> result = noteSearch.searchNotes("Test Note 1");
        assert result.size() == 1;
        assert result.get(0).getTitle().equals("Test Note 1");
    }

    @Test
    public void testSearchNotesByContent() {
        JsonNoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        NoteSearch noteSearch = new NoteSearch(noteRepository);

        Note note1 = new Note("1", "Test Note 1");
        note1.addContent(new NoteContent("This is the content of test note 1"));
        noteRepository.writeNote(note1);

        Note note2 = new Note("2", "Another Test Note");
        note2.addContent(new NoteContent("This is the content of another test note"));
        noteRepository.writeNote(note2);

        List<Note> result = noteSearch.searchNotes("content of another test note");
        assert result.size() == 1;
        assert result.get(0).getTitle().equals("Another Test Note");
    }

    @Test
    public void testSearchNotesNoMatch() {
        JsonNoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        NoteSearch noteSearch = new NoteSearch(noteRepository);

        Note note1 = new Note("1", "Test Note 1");
        note1.addContent(new NoteContent("This is the content of test note 1"));
        noteRepository.writeNote(note1);

        Note note2 = new Note("2", "Another Test Note");
        note2.addContent(new NoteContent("This is the content of another test note"));
        noteRepository.writeNote(note2);

        List<Note> result = noteSearch.searchNotes("Non-existent keyword");
        assert result.size() == 0;
    }

    @Test
    public void testSearchEmptyString() {
        JsonNoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        NoteSearch noteSearch = new NoteSearch(noteRepository);

        Note note1 = new Note("1", "Test Note 1");
        note1.addContent(new NoteContent("This is the content of test note 1"));
        noteRepository.writeNote(note1);

        Note note2 = new Note("2", "Another Test Note");
        note2.addContent(new NoteContent("This is the content of another test note"));
        noteRepository.writeNote(note2);

        List<Note> result = noteSearch.searchNotes("");
        assert result.size() > 0;
    }
}
