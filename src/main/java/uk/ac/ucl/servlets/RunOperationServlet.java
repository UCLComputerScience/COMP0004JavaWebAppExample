package uk.ac.ucl.servlets;

import jdk.jfr.Frequency;
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
import java.util.List;

class OPERATION{
    static final String FIND_HIGHEST = "1";
    static final String FIND_LOWEST = "2";
    static final String SORT = "3";
}

@WebServlet("/operations.html")
public class RunOperationServlet extends HttpServlet {
    Model model;
    HttpServletRequest request;
    HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        initPage();

        String pageNotInitialised = request.getParameter("id");
        if (pageNotInitialised == null){
            handleInitedPage();
            request.setAttribute("initialised", "1");
        }

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/operations.jsp");
        dispatch.forward(request,response);
    }

    private void initPage() throws IOException {
        this.model = ModelFactory.getModel();
        HashMap<String, List<String>> optionCollection = model.getAvailOptionCollection();
        List<String> availOptions = optionCollection.keySet().stream().toList();

        request.setAttribute("filterCollections", optionCollection);
        request.setAttribute("availOptions", availOptions);
    }

    private void handleInitedPage(){
        String filterOption = request.getParameter("filterOptions");
        String filterValue = request.getParameter("filtValue");
        String operation = request.getParameter("operation");

        List<Integer> filteredIndex = model.getFilteredPatient(filterOption, filterValue);

        request.setAttribute("sorted", false);
        if (operation == null){
            displayFilteredPatients(filteredIndex);
        } else {
            switch(operation){
                case OPERATION.FIND_HIGHEST -> findHighestAgeIn(filteredIndex);
                case OPERATION.FIND_LOWEST -> findLowestAgeIn(filteredIndex);
                case OPERATION.SORT -> sort(filteredIndex);
            }
        }
    }

    private void displayFilteredPatients(List<Integer> filteredPatientsIndex) {
        setAttributeOf(filteredPatientsIndex);
    }

    private void findHighestAgeIn(List<Integer> filteredPatientsIndices){
        List<Integer> highestAgeIndex = model.findHighestAge(filteredPatientsIndices);
        setAttributeOf(highestAgeIndex);
    }

    private void findLowestAgeIn(List<Integer> filteredPatientsIndices){
        List<Integer> LowestAgeIndex = model.findLowestAge(filteredPatientsIndices);
        setAttributeOf(LowestAgeIndex);
    }

    private void sort(List<Integer> filteredPatients) {
        String sortMethod = request.getParameter("sortMethod");
        String sortValue = request.getParameter("sortValue");
        List<Integer> sortedPatientsIndex = model.sortBy(filteredPatients, sortMethod, sortValue);
        request.setAttribute("sorted", true);
        request.setAttribute("sortOrder", sortedPatientsIndex);
        setAttributeOf(sortedPatientsIndex);
    }

    private void setAttributeOf(List<Integer> resultPatientsIndex){
        HashMap<Integer,String> resultPatients = createIndexToPatientMap(resultPatientsIndex);
        request.setAttribute("patientsToDisplay", resultPatients);
    }

    private HashMap<Integer, String> createIndexToPatientMap(List<Integer> filteredIndex){
        return model.getPatientList(filteredIndex);
    }

}


