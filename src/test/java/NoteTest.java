import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.TextContent;

public class NoteTest {
    @Test
    public void testNoteCreation() {
        Note note = new Note("123321", "Test Note");
        assert note.getId().equals("123321");
        assert note.getTitle().equals("Test Note");
    }

    @Test
    public void testNoteTitleChange() {
        Note note = new Note("123321", "Test Note");
        note.setTitle("New Title");
        assert note.getTitle().equals("New Title");
    }

    @Test
    public void testNoteContent() {
        Note note = new Note("123321", "Test Note");
        note.addContent(new TextContent("This is a test note"));
        assert note.getAllContent().size() == 1;
        assert note.getAllContent().get(0).getContent().equals("This is a test note");
    }
}