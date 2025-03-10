<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
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
            List<Note> notes = (List<Note>) request.getAttribute("notes");
            for (Note note : notes) {
                String title = note.getTitle();
                String href = "note.html?noteId=" + note.getId();
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
