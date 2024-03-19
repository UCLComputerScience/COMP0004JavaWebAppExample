<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operations</title>
    <jsp:include page="/header.jsp"/>
    <script>
        <%-- Dynamic Drop-down Boxes--%>
        var availObject = {}
        <%
        HashMap filterCollections = (HashMap<String, List>) request.getAttribute("filterCollections");
        List<String> availOptions = (List<String>) request.getAttribute("availOptions");

        for (String option: availOptions){
            List<String> availValues = (List<String>) filterCollections.get(option); %>
            availObject["<%=option%>"] = [];
        <% for (String value: availValues){ %>
                availObject["<%=option%>"].push("<%=value%>")
        <%
            }
        }
        %>

        window.onload = function() {
            var optionSelect = document.getElementById("filterOptions");
            var valueSelect = document.getElementById("filtValue");
            for (var x in availObject){
                optionSelect.options[optionSelect.options.length] = new Option(x,x);
            }

            optionSelect.onchange = function() {
               valueSelect.length = 1;
               var y = availObject[this.value];
               for (var i = 0; i < y.length; i ++){
                   valueSelect.options[valueSelect.options.length] = new Option(y[i], y[i]);
               }
            }
        }
    </script>
</head>
<body>
<h2>Patients Operations</h2>
<h3>Choose the operation from below</h3>
<form action="/operations.html" id="1">

    <input type="radio" id="findHighest"  name="operation" value = "1">
    <label for="findHighest">Find Highest Age</label> <br>

    <input type="radio" id="findLowest" name="operation" value="2">
    <label for="findLowest">Find Lowest Age</label> <br>

    <input type="radio" id="sort" name="operation" value="3">
    <label for="sort">Sort
        <select name="sortMethod" id="sortMethod">
            <option value = "Descending">High to Low</option>
            <option value = "Ascending" selected>Low to High</option>
        </select> by

        <select name="sortValue" id="sortValue" >
            <option value="FIRST">first name</option>
            <option value="LAST">last name</option>
            <option value="AGE">age</option>
        </select>
    </label>

    <h3> Filter </h3>
    <p>From
    <select name="filterOptions" id="filterOptions">
        <option value="" selected="selected">Select All</option>
    </select>
    show only
    <select name="filtValue" id="filtValue">
        <option value="" selected="selected">All</option>
    </select><br><br>
    <input type="submit" value="Submit">
    </p>

</form>
<%--show result patient list--%>
<% if(request.getAttribute("initialised") != null){%>
    <%
        HashMap<Integer, String> patientList = (HashMap<Integer, String>) request.getAttribute("patientsToDisplay"); %>
    <p>The result contains <%=patientList.size()%> patients</p>
    <%
        if ((Boolean) request.getAttribute("sorted")){
            List<Integer> displayOrder = (List<Integer>) request.getAttribute("sortOrder");
            for(Integer id: displayOrder){
                String href = "patientinfo.html?"
                        + "id=" + id; %>
                <li><a href ="<%=href%>"><%=patientList.get(id)%></a></li>
            <% }

        } else {
            for(Integer id: patientList.keySet()){
                String href = "patientinfo.html?"
                        + "id=" + id;%>
                <li><a href ="<%=href%>"><%=patientList.get(id)%></a></li>
            <% }
        } %>
<% } %>

</body>
</html>
