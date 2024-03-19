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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/addPatient.html")
public class AddNewPatientServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        HashMap<String, String> infoOFPatient = new HashMap<>();
        List<String> columns = model.getColumnNames();
        for(String column: columns){
            infoOFPatient.put(column,request.getParameter(column));
        }

        ModelFactory.addPatientAndUpdateModel(infoOFPatient);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/redirectToMenu.jsp");
        dispatch.forward(request, response);
    }
}
