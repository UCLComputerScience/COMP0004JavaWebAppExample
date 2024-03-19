<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient List</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
<%--  add patient button--%>
  <form action="/addPatientPage.html">
    <button name="add patient">Add patient</button>
  </form>
<%--  display s list of patients--%>
  <ul>
    <%
      List<String> patients = (List<String>) request.getAttribute("patients");
      for (int index = 0; index < patients.size(); index ++)
      {
        String name = patients.get(index);
        String href = "patientinfo.html?id=" + index; %>
        <li><a href="<%=href%>"><%=name%></a></li>
      <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
