import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteContent;

import java.util.ArrayList;
import java.util.List;

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
        note.addContent(new NoteContent("This is a test note"));
        assert note.getContents().size() == 1;
        assert note.getContents().get(0).getContent().equals("This is a test note");
        note.addContent(new NoteContent("This is another test note"));
        assert note.getContents().size() == 2;
        List<NoteContent> contents = new ArrayList<>();
        contents.add(new NoteContent("This is the only test note"));
        note.setContents(contents);
        assert note.getContents().size() == 1;
        assert note.getContents().get(0).getContent().equals("This is the only test note");
    }
}