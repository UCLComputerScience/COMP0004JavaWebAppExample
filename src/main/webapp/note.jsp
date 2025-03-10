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
<div class="main">
    <h2><%=note.getTitle()%>
    </h2>
    <form action="saveNote.html" method="post">
        <input type="hidden" name="noteId" value="<%=note.getId()%>">
        <label for="noteTitle">Title:</label>
        <input type="text" id="noteTitle" name="noteTitle" value="<%=note.getTitle()%>">
        <label for="noteContent">Content:</label>
        <% for (NoteContent content : note.getContents()) { %>
        <textarea id="noteContent" name="noteContent"><%=content.getContent()%></textarea>
        <% } %>
        <button type="submit">Save</button>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
