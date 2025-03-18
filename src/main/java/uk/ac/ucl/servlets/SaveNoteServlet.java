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
        String[] contentTypes = request.getParameterValues("contentType");

        Note note = noteService.getNoteById(noteId);
        note.setTitle(noteTitle);

        List<NoteContent> contents = new ArrayList<>();
        if (noteContents != null && contentTypes != null && noteContents.length == contentTypes.length) {
            for (int i = 0; i < noteContents.length; i++) {
                String content = noteContents[i];
                String contentType = contentTypes[i];
                if (content != null && !content.trim().isEmpty() && contentType != null && !contentType.trim().isEmpty()) {
                    String formattedContent = content.replace("\r\n", "<br>")
                            .replace("\n", "<br>");
                    contents.add(new NoteContent(formattedContent, contentType));
                }
            }
        }
        note.setContents(contents);

        noteService.updateNote(note);

        response.sendRedirect("note.html?noteId=" + noteId);
    }
}
