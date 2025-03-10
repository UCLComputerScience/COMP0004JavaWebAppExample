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
import java.util.List;

// The servlet invoked to display a list of patients. Note that this data is just example data,
// you replace it with your data.
// The url http://localhost:8080/patientList.html is mapped to calling doGet on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/noteList.html")
public class ViewNoteListServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        NoteService noteService = NoteServiceFactory.getNoteService();
        List<Note> notes = noteService.getAllNotes();
        request.setAttribute("notes", notes);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteList.jsp");
        dispatch.forward(request, response);
    }
}
