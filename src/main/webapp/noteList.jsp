<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>WebNote</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <label for="categorySelector">You can choose a category to view:</label>
    <select id="categorySelector" onchange="location = this.value;">
        <option value="noteList.html">All</option>
        <%
            Set<String> categories = (Set<String>) request.getAttribute("categories");
            if (categories != null && !categories.isEmpty()) {
                for (String category : categories) {
                    String href = "noteList.html?category=" + category;

        %>
        <option value="<%=href%>"><%=category%>
        </option>
        <% }
        } else { %>
        <option value="noteList.html">No other categories available</option>
        <%} %>
    </select>
    <button onclick="showCreateCategoryForm()">Create New Category</button>
    <form id="createCategoryForm" action="createCategory.html" method="post" style="display:none;">
        <label for="categoryName">Category Name:</label>
        <input type="text" id="categoryName" name="categoryName" required>
        <button type="submit">Create</button>
    </form>
    <h2>Notes:</h2>
    <ul>
        <%
            List<Note> notes = (List<Note>) request.getAttribute("notes");
            for (Note note : notes) {
                String title = note.getTitle();
                String href = "note.html?noteId=" + note.getId();
        %>
        <li>
            <a href="<%=href%>"><%=title%>
            </a>
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
