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
    <div contenteditable="true" id="noteTitle" class="noteTitle"><%=note.getTitle()%>
    </div>
    <div id="noteContents">
        <% for (NoteContent content : note.getContents()) { %>
        <div contenteditable="true" class="noteContent" onblur="saveNote()"><%=content.getContent()%>
        </div>
        <% } %>
    </div>
    <button id="addContentButton">Add Content</button>
    <button id="saveButton">Save</button>
</div>
<jsp:include page="/footer.jsp"/>
<script>
    function saveNote() {
        let noteId = "<%=note.getId()%>";
        let noteTitle = document.getElementById("noteTitle").innerText;
        let contentDivs = document.getElementsByClassName("noteContent");

        let formData = "noteId=" + encodeURIComponent(noteId) + "&noteTitle=" + encodeURIComponent(noteTitle.trimEnd());

        for (let i = 0; i < contentDivs.length; i++) {
            formData += "&noteContent=" + encodeURIComponent(contentDivs[i].innerText.trimEnd());
        }

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "saveNote.html", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.href = "noteList.html";
            }
        };
        xhr.send(formData);
    }

    document.getElementById("addContentButton").addEventListener("click", saveNote);

    document.getElementById("saveButton").addEventListener("click", function () {
        let noteId = "<%=note.getId()%>";
        let noteTitle = document.getElementById("noteTitle").innerText;
        let contentDivs = document.getElementsByClassName("noteContent");

        let formData = "noteId=" + encodeURIComponent(noteId) + "&noteTitle=" + encodeURIComponent(noteTitle.trimEnd());

        for (let i = 0; i < contentDivs.length; i++) {
            formData += "&noteContent=" + encodeURIComponent(contentDivs[i].innerText.trimEnd());
        }

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "saveNote.html", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.href = "noteList.html";
            }
        };
        xhr.send(formData);
    });
</script>
</body>
</html>
