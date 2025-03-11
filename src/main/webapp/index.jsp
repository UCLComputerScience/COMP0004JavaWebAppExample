<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <jsp:include page="/meta.jsp"/>
    <title>WebNote Home</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Welcome to WebNote</h2>
    <p class="intro">Your personal digital notebook for capturing thoughts, ideas, and important information.</p>

    <div class="feature-cards">
        <div class="feature-card">
            <h3>Manage Notes</h3>
            <p>Create, view, and organize all your notes in one place.</p>
            <a href="noteList.html" class="feature-button">View Notes</a>
        </div>

        <div class="feature-card">
            <h3>Quick Search</h3>
            <p>Find exactly what you need with powerful search.</p>
            <a href="search.jsp" class="feature-button">Search Notes</a>
        </div>

        <div class="feature-card">
            <h3>Create New</h3>
            <p>Start capturing your ideas with a new note.</p>
            <a href="createNote.html" class="feature-button">Create Note</a>
        </div>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>