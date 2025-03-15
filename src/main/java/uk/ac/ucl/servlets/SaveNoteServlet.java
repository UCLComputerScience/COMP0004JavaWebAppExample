package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteContent;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/saveNote.html")
public class SaveNoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoteService noteService = NoteServiceFactory.getNoteService();
        String noteId = request.getParameter("noteId");
        String noteTitle = request.getParameter("noteTitle");
        String[] noteContents = request.getParameterValues("noteContent");

        Note note = noteService.getNoteById(noteId);
        note.setTitle(noteTitle);

        List<NoteContent> contents = new ArrayList<>();
        if (noteContents != null) {
            for (String content : noteContents) {
                if (content != null && !content.trim().isEmpty()) {
                    String formattedContent = content.replace("\r\n", "<br>")
                            .replace("\n", "<br>");
                    contents.add(new NoteContent(formattedContent));
                }
            }
        }
        note.setContents(contents);

        noteService.updateNote(note);

        response.sendRedirect("noteList.html");
    }
}
