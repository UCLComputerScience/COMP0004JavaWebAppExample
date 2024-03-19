package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
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

/**
 * Invoke the page that displays all info about the patient
 */
@WebServlet("/patientinfo.html")
public class ViewPatientInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            Model model = ModelFactory.getModel();
            String id= request.getParameter("id");

            HashMap<String, String> patientInfo = model.getPatientInfo(id);
            request.setAttribute("patientInfo", patientInfo);
            request.setAttribute("id", id);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/patientInfo.jsp");
            dispatch.forward(request, response);

    }
}
