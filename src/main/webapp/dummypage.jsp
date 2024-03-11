<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
  <title>A Patient</title>
</head>
<%--<body>--%>
<h1>Patient Info</h1>>
<ul>
  <%
    List<String> patientInfo = (List<String>) request.getAttribute("patientInfo");
    for(String info: patientInfo){
  %>
  <li>
    <%=info%>
  </li>
  <% } %>
</ul>
</body>
</html>