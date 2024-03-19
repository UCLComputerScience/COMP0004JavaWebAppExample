<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operations</title>
    <jsp:include page="/header.jsp"/>
    <script>
        <%-- Dynamic Drop-down Boxes Column -> Values --%>
        var availObject = {}
        // initialise the availObject
        <%
        HashMap options = (HashMap<String, List>) request.getAttribute("options");
        List<String> availColumns = (List<String>) request.getAttribute("availColumns");

        for (String column: availColumns){
            List<String> availValues = (List<String>) options.get(column); %>
            availObject["<%=column%>"] = [];
        <% for (String value: availValues){ %>
                availObject["<%=column%>"].push("<%=value%>")
        <%
            }
        }
        %>

        window.onload = function() {
            var optionSelect = document.getElementById("filterOptions");
            var valueSelect = document.getElementById("filterValue");
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
<form action="/operations.html?">
<%--find highest age--%>
    <input type="radio" id="findHighest"  name="operation" value = "1">
    <label for="findHighest">Find Highest Age</label> <br>
<%--find smallest age--%>
    <input type="radio" id="findLowest" name="operation" value="2">
    <label for="findLowest">Find Lowest Age</label> <br>
<%--sort by value--%>
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
<%--Filter by column value--%>
    <h3> Filter </h3>
    <p>From
    <select name="filterOptions" id="filterOptions">
        <option value="" selected="selected">Select All</option>
    </select>
    show only
    <select name="filterValue" id="filterValue">
        <option value="" selected="selected">All</option>
    </select><br><br>
    <button type="submit" name="pageInitialised" value="1">Submit</button>
    </p>

</form>
<%--show result patient list--%>
<% if((Boolean) request.getAttribute("initialised")){%>
    <%
        HashMap<Integer, String> patientList = (HashMap<Integer, String>) request.getAttribute("patientsToDisplay"); %>
    <p>The result contains <%=patientList.size()%> patients</p>
    <%
        if ((Boolean) request.getAttribute("sorted")){
            List<Integer> displayOrder = (List<Integer>) request.getAttribute("order");
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
