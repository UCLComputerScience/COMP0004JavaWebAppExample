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
import java.util.List;

@WebServlet("/dummypage.html")
public class ViewPatientInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            Model model = ModelFactory.getModel();
            List<String> patientInfo = model.getPatientInfo(request.getParameter("id"));
            request.setAttribute("patientInfo", patientInfo);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/dummypage.jsp");
            dispatch.forward(request, response);

    }
}
