package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;

@WebServlet("/uploadImage.html")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");
        Part filePart = request.getPart("imageFile");
        // filePart.write("test.jpeg");
        NoteService noteService = NoteServiceFactory.getNoteService();
        String imageSrc = noteService.uploadImage(filePart.getInputStream(), filePart.getSubmittedFileName());
        Note note = noteService.getNoteById(noteId);
        note.appendImage(imageSrc);
        noteService.updateNote(note);

        response.sendRedirect("note.html?noteId=" + noteId);
    }
}
