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
    <meta http-equiv="refresh" content="0;url=patientinfo.html?id=<%=request.getAttribute("id").toString()%>">
</head>
<body>
    <p>edit successful, redirect to updated patient</p>
</body>
</html>
