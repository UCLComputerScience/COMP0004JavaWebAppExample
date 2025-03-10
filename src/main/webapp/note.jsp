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
    <div contenteditable="true" id="noteTitle"><%=note.getTitle()%></div>
    <div id="noteContents">
        <% for (NoteContent content : note.getContents()) { %>
        <div contenteditable="true" class="noteContent"><%=content.getContent()%></div>
        <% } %>
    </div>
    <button id="addContentButton">Add Content</button>
    <button id="saveButton">Save</button>
</div>
<jsp:include page="/footer.jsp"/>
<script>
    document.getElementById("addContentButton").addEventListener("click", function() {
        var newContentDiv = document.createElement("div");
        newContentDiv.contentEditable = "true";
        newContentDiv.className = "noteContent";
        document.getElementById("noteContents").appendChild(newContentDiv);
    });

    document.getElementById("saveButton").addEventListener("click", function() {
        var noteId = "<%=note.getId()%>";
        var noteTitle = document.getElementById("noteTitle").innerText;
        var noteContents = [];
        var contentDivs = document.getElementsByClassName("noteContent");
        for (var i = 0; i < contentDivs.length; i++) {
            noteContents.push(contentDivs[i].innerText);
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "saveNote.html", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.href = "noteList.html";
            }
        };
        xhr.send("noteId=" + encodeURIComponent(noteId) + "&noteTitle=" + encodeURIComponent(noteTitle) + "&noteContent=" + encodeURIComponent(noteContents.join(",")));
    });
</script>
</body>
</html>
