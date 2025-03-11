<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <jsp:include page="/meta.jsp"/>
    <title>Search Notes</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Search Notes</h2>
    <p class="search-description">Find notes by keywords, titles or content.</p>

    <div class="search-container">
        <form method="POST" action="/searchNotes.html">
            <div class="search-field">
                <input type="text" name="searchstring" placeholder="Enter search keyword here" autofocus/>
                <input type="submit" value="Search"/>
            </div>
            <div class="search-tips">
                <p>Try searching for specific words or phrases that appear in your notes.</p>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>