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
        <li>
            <a href="<%=href%>"><%=title%></a>
            <button onclick="deleteNote('<%=note.getId()%>')">Delete</button>
        </li>
        <% } %>
    </ul>
    <form action="createNote.html" method="get">
        <button type="submit">Create New Note</button>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
<script>
    function deleteNote(noteId) {
        if (confirm("Are you sure you want to delete this note?")) {
            var form = document.createElement("form");
            form.method = "post";
            form.action = "noteList.html";

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "noteId";
            input.value = noteId;

            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>
</body>
</html>
