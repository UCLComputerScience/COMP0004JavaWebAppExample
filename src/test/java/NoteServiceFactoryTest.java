import org.junit.jupiter.api.Test;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.util.List;

public class NoteServiceFactoryTest {
    @Test
    public void testGetNoteService() {
        NoteService noteService = NoteServiceFactory.getNoteService();
        assert noteService != null;
        List<Note> notes = noteService.searchNotes("");
        assert notes != null;
        assert !notes.isEmpty();
    }

}
