package uk.ac.ucl.servlets;

import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Model;

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

@WebServlet("/statschart.html")
public class StatsChartServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        HashMap<String, Integer> distributionByAge = model.getAgeDistribution();
        List<String> ageGroups = model.getAgeGroups();

        request.setAttribute("ageGroups", ageGroups);
        request.setAttribute("distributionByAge", distributionByAge);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/statschart.jsp");
        dispatch.forward(request,response);
    }
}
