<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>WebNote</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Notes:</h2>
    <ul>
        <%
            List<String> noteTitles = (List<String>) request.getAttribute("noteTitles");
            for (String title : noteTitles) {
                String href = "dummypage.html";
        %>
        <li><a href="<%=href%>"><%=title%>
        </a>
        </li>
        <% } %>
    </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
