<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Search Result</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h1>Search Result</h1>
    <%
        List<uk.ac.ucl.model.Note> notes = (List<Note>) request.getAttribute("result");
        if (notes.size() != 0) {
    %>
    <ul>
        <%
            for (uk.ac.ucl.model.Note note : notes) {
        %>
        <li>
            <h3><%=note.getTitle()%>
            </h3>
            <%
                for (uk.ac.ucl.model.NoteContent content : note.getContents()) {
            %>
            <p><%=content.getContent()%>
            </p>
            <% } %>
        </li>
        <% }
        } else {%>
        <p>Nothing found</p>
        <%}%>
    </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
