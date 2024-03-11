<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
  <title>A Patient</title>
</head>
<%--<body>--%>
<h1>patient</h1>>
  <%
    System.out.println("entered");
    List<String> patientInfo = (List<String>) request.getAttribute("patientInfo");
    System.out.println(patientInfo);
  %>
<li><a> patientInfo </a></li>
</body>
<%--<form action = "\ViewPatientinfoServlet" method = "get" ><textarea></form>--%>
</html>