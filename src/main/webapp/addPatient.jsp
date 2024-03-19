<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add patient</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<h3>Values of new patient </h3>
<form action="/addPatient.html">
    <%List<String> columns = (List<String>) request.getAttribute("columns");
        for (String column: columns){ %>
    <li>
        <label for="<%=column%>"><%=column%></label>
        <input type="text" id = "<%=column%>" name="<%=column%>" value="">
    </li>
    <br>
    <% } %>
    <input type="submit" value="Save">
</form>
</body>
</html>
