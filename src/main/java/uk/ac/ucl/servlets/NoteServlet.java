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

@WebServlet("/note.html")
public class NoteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        NoteService noteService = NoteServiceFactory.getNoteService();
        String noteId = request.getParameter("noteId");
        Note note = noteService.getNoteById(noteId);
        request.setAttribute("note", note);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/note.jsp");
        dispatch.forward(request, response);
    }
}
