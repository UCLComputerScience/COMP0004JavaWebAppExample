<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search Result</h1>
  <%
    HashMap<Integer, String> results = (HashMap<Integer, String>) request.getAttribute("result");
    if (results.size() !=0)
    {
    %>
    <ul>
      <%
        for (int id: results.keySet())
        {
          String href = "patientinfo.html"
                  + "?id=" + id;
      %>
      <li><a href="<%=href%>"><%=results.get(id)%></a></li>
      <%}
    } else {%>
      <p>Nothing found</p>
  <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>