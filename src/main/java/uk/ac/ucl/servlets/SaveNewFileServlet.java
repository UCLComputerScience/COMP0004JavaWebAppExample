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

@WebServlet("/saveNewFile.html")
public class SaveNewFileServlet extends HttpServlet {
    private static final String FILE_PATH_PREFIX = "user/";
    private static final String FILE_PATH_SUFFIX = ".json";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newFilePath = FILE_PATH_PREFIX + request.getParameter("newFileName") + FILE_PATH_SUFFIX;
        System.out.println(newFilePath);
        ModelFactory.saveFile(newFilePath);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/menu.html");
        dispatch.forward(request,response);
    }

}
