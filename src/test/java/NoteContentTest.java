import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.NoteContent;

public class NoteContentTest {
    @Test
    public void testTextContentCreation() {
        NoteContent noteContent = new NoteContent("This is a test note");
        assert noteContent.getContent().equals("This is a test note");
    }
}
