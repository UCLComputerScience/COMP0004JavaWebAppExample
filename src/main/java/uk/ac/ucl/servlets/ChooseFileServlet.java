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


@WebServlet("/menu.html")
public class ChooseFileServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = request.getParameter("fileOptions");
        ModelFactory.initFileBase(file);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/menu.jsp");
        dispatch.forward(request,response);
    }

}
