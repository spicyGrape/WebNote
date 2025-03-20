<%@ page import="java.util.Set" %>
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
        <form method="POST" action="searchNotes.html">
            <div class="search-field">
                <label>
                    <input type="text" name="searching" placeholder="Enter search keyword here" autofocus/>
                </label>
                <input type="submit" value="Search"/>
            </div>
            <label for="category">Search in Category:</label>
            <select name="category" id="category">
                <option value="all">All Categories</option>
                <%
                    Set<String> categoryNameCollection = (Set<String>) request.getAttribute("categoryNameCollection");
                    if (categoryNameCollection != null) {
                        for (String categoryName : categoryNameCollection) {
                %>
                <option value="<%= categoryName %>"><%= categoryName %>
                </option>
                <%
                        }
                    }
                %>

            </select>
            <div class="search-tips">
                <p>Try searching for specific words or phrases that appear in your notes.</p>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>