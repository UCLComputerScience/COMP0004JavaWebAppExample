<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.NoteContent" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <% Note note = (Note) request.getAttribute("note"); %>
    <title><%=note.getTitle()%>
    </title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main"></div>
<h2><%=note.getTitle()%>
</h2>
<% for (NoteContent content : note.getContents()) { %>
<p><%=content.getContent()%>
</p>
<% } %>
<jsp:include page="/footer.jsp"/>
</body>
</html>
