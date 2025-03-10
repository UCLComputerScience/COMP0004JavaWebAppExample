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

@WebServlet("/searchNotes.html")
public class SearchServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    NoteService noteService = NoteServiceFactory.getNoteService();
    List<Note> searchResult = noteService.searchNotes(request.getParameter("searchstring"));
    request.setAttribute("result", searchResult);

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
    dispatch.forward(request, response);
  }
}
