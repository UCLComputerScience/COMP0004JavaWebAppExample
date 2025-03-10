package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The servlet invoked to display a list of patients. Note that this data is just example data,
// you replace it with your data.
// The url http://localhost:8080/patientList.html is mapped to calling doGet on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/noteList.html")
public class ViewNoteListServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the data from the model
        NoteService noteService = NoteServiceFactory.getNoteService();
        List<Note> notes = noteService.searchNotes("");
        List<String> noteTiles = new ArrayList<>();
        for (Note note : notes) {
            noteTiles.add(note.getTitle());
        }
        // Then add the data to the request object that will be sent to the Java Server Page, so that
        // the JSP can access the data (a Java data structure).
        request.setAttribute("noteTitles", noteTiles);

        // Invoke the JSP.
        // A JSP page is actually converted into a Java class, so behind the scenes everything is Java.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteList.jsp");
        dispatch.forward(request, response);
    }
}
