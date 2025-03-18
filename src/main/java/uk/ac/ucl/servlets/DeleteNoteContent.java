package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;

@WebServlet("/deleteNoteContent.html")
@MultipartConfig
public class DeleteNoteContent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");
        Integer contentIndex = Integer.parseInt(request.getParameter("contentIndex"));
        NoteService noteService = NoteServiceFactory.getNoteService();
        Note note = noteService.getNoteById(noteId);
        if (note != null && contentIndex != null && contentIndex >= 0 && contentIndex < note.getContents().size()) {
            note.getContents().get(contentIndex).deleteContent();
            note.getContents().remove(contentIndex.intValue());
            noteService.updateNote(note);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid note ID or content index");
            return;
        }

        response.sendRedirect("note.html?noteId=" + noteId);
    }
}
