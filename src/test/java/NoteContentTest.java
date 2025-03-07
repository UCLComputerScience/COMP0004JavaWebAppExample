import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.NoteContent;
import uk.ac.ucl.model.TextContent;

public class NoteContentTest {
    @Test
    public void testTextContentCreation() {
        NoteContent noteContent = new TextContent("This is a test note");
        assert noteContent.getContent().equals("This is a test note");
    }
}
