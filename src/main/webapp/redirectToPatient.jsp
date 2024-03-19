<%--
  Created by IntelliJ IDEA.
  User: aiden
  Date: 18/03/2024
  Time: 09:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirect to updated patient</title>
    <%String url = "patientinfo.html?id=" + request.getAttribute("id");%>
    <meta http-equiv="refresh" content="3;url=<%=url%>">
</head>
<body>
    <p>edit successful, redirect to updated patient in 3 seconds</p>
    <a href = "<%=url%>">press here if you are not automatically directed</a>
</body>
</html>
