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

@WebServlet("/createNote.html")
public class CreateNoteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        NoteService noteService = NoteServiceFactory.getNoteService();
        Note newNote = noteService.createNote();
        response.sendRedirect("note.html?noteId=" + newNote.getId());
    }
}
