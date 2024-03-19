package uk.ac.ucl.servlets;

import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/editpatient.html")
public class EditPatientServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String column = request.getParameter("columnToEdit");
        int id = Integer.parseInt(request.getParameter("editId"));
        String newValue = request.getParameter("newValue");

        ModelFactory.editPatientAndUpdateModel(id, column, newValue);
        request.setAttribute("id", id);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/redirectToPatient.jsp");
        dispatch.forward(request, response);
    }

}

