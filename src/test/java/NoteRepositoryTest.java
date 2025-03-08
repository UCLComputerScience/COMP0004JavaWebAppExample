import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.JsonNoteRepository;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteContent;
import uk.ac.ucl.model.NoteRepository;

public class NoteRepositoryTest {
    String testIndexPath = "temp/testIndex.json";
    String testNotesDirectory = "temp/notes/";

    @Test
    public void testNoteRepositoryCreation() {
        NoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        assert noteRepository != null;
        Note testNote = new Note("1", "Test Note");
        testNote.addContent(new NoteContent("This is a test note"));
        noteRepository.writeNote(testNote);
    }

    @Test
    public void testNoteRepositoryRead() {
        NoteRepository noteRepository = new JsonNoteRepository(testIndexPath, testNotesDirectory);
        Note testNote = noteRepository.loadNoteById("1");
        assert testNote != null;
        assert testNote.getTitle().equals("Test Note");
        assert testNote.getContents().get(0).getContent().equals("This is a test note");
    }

}
