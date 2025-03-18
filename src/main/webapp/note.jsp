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
        <% int contentSize = note.getContents().size();%>
        <% for (int i = 0; i < contentSize; i++) { %>
        <% NoteContent content = note.getContents().get(i); %>
        <div>
            <div contenteditable="true" class="noteContent" data-content-type="<%=content.getContentType()%>">
                <% if ("text".equals(content.getContentType())) { %>
                <p><%=content.getContent()%>
                </p>
                <% } else if ("image".equals(content.getContentType())) { %>
                <img src="${pageContext.request.contextPath}/image?file=<%=content.getContent()%>" alt="Image content">
                <% } else if ("url".equals(content.getContentType())) { %>
                <a href="<%=content.getContent()%>"><%=content.getContent()%>
                </a>
                <% } else if ("html".equals(content.getContentType())) { %>
                <%=content.getContent()%>
                <% } %>
            </div>
            <form action="deleteNoteContent.html" method="post">
                <input type="hidden" name="noteId" value="<%=note.getId()%>">
                <input type="hidden" name="contentIndex" value="<%=i%>">
                <button type="submit" class="delete-button">Delete</button>
            </form>
        </div>
        <% } %>
    </div>
    <!-- Image Upload form (hidden) -->
    <form id="imageUploadForm" action="uploadImage.html" method="post" enctype="multipart/form-data"
          style="display:none;">
        <input type="file" name="imageFile" id="imageFile">
        <input type="hidden" name="noteId" value="<%=note.getId()%>">
        <input type="submit" value="Upload Image">
    </form>
    <button id="addTextButton" onclick="addNewContentDiv('text')">Add Text</button>
    <button id="addImageButton" onclick="showImageUploadForm()">Add Image</button>
    <button id="addUrlButton" onclick="addNewContentDiv('url')">Add URL</button>
    <button id="addHtmlButton" onclick="addNewContentDiv('html')">Add HTML</button>
    <button id="saveButton">Save</button>
</div>
<jsp:include page="/footer.jsp"/>


<script>
    function saveNote() {
        let noteId = "<%=note.getId()%>";
        let noteTitle = document.getElementById("noteTitle").innerText;
        let contentDivs = document.getElementsByClassName("noteContent");

        let formData = "noteId=" + encodeURIComponent(noteId) + "&noteTitle=" + encodeURIComponent(noteTitle);

        for (let i = 0; i < contentDivs.length; i++) {
            let contentType = contentDivs[i].getAttribute("data-content-type");
            let contentValue;
            if (contentType === "text") {
                contentValue = contentDivs[i].innerText;
            } else if (contentType === "html") {
                contentValue = contentDivs[i].innerHTML;
            } else if (contentType === "image") {
                console.log(contentDivs[i].innerHTML);
                const regex = /src="\/image\?file=([^"]+)"/;
                const match = contentDivs[i].innerHTML.match(regex);
                console.log(match);
                contentValue = match ? match[1] : "";
            } else if (contentType === "url") {
                const match = contentDivs[i].innerHTML.match(/href="([^"]+)">/);
                contentValue = match ? match[1] : "";
            }
            // Ignore empty content and empty strings
            if (!contentValue || contentValue.trim() === "") {
                continue;
            }
            // Append content type and value to formData
            formData += "&noteContent=" + encodeURIComponent(contentValue) + "&contentType=" + encodeURIComponent(contentType);
        }

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "saveNote.html", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.send(formData);
    }

    function addNewContentDiv(contentType) {
        let newContentDiv = document.createElement("div");
        newContentDiv.contentEditable = "true";
        newContentDiv.className = "noteContent";
        newContentDiv.setAttribute("data-content-type", contentType);
        newContentDiv.addEventListener("blur", saveNote);

        if (contentType === "image") {
            let img = document.createElement("img");
            img.src = "";
            newContentDiv.appendChild(img);
        } else if (contentType === "url") {
            let a = document.createElement("a");
            a.href = "";
            a.innerText = "New URL";
            newContentDiv.appendChild(a);
        }

        let newButton = document.createElement("button");
        newButton.className = "delete-button";
        newButton.innerText = "Delete";
        newButton.onclick = function () {
            deleteContent(this);
        };
        let newContentButtonDiv = document.createElement("div");
        newContentButtonDiv.appendChild(newContentDiv);
        newContentButtonDiv.appendChild(newButton);
        document.getElementById("noteContents").appendChild(newContentButtonDiv);
    }

    function deleteContent(button) {
        let contentDiv = button.parentElement;
        contentDiv.innerHTML = "";
        saveNote();
    }

    function showImageUploadForm() {
        let form = document.getElementById("imageUploadForm");
        form.style.display = "block";
    }

    document.getElementById("saveButton").addEventListener("click", saveNote);
    let contentDivs = document.getElementsByClassName("noteContent");
    for (let i = 0; i < contentDivs.length; i++) {
        contentDivs[i].addEventListener("blur", saveNote);
    }
</script>
</body>
</html>
