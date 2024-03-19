<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add patient</title>
    <% final List<String> DISPLAY_SEQUENCE = Arrays.asList("PREFIX","FIRST", "LAST", "SUFFIX","MAIDEN","GENDER", "ID","BIRTHDATE","DEATHDATE","BIRTHPLACE", "SSN", "DRIVERS", "PASSPORT", "MARITAL", "RACE", "ETHNICITY", "ADDRESS", "CITY", "STATE", "ZIP");%>
    <jsp:include page="header.jsp"/>
</head>
<body>
<h3>Values of new patient </h3>
<form action="/addPatient.html">
    <%
        for (String column: DISPLAY_SEQUENCE){ %>
    <li>
        <label for="<%=column%>"><%=column%></label>
        <input type="text" id = "<%=column%>" name="<%=column%>" value="">
    </li>
    <br>
    <% } %>
    <input type="submit" value="Save">
</form>
<p>*Date format: YYYY-mm-dd</p>
</body>
</html>
