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
    <%
        String currentCategory = (String) request.getAttribute("selectedCategory");
        if (currentCategory == null || currentCategory.isEmpty()) {
            currentCategory = "All";
        }
    %>
    <select id="categorySelector" onchange="location = this.value;">
        <option value="noteList.html"><%=currentCategory%>
        </option>

        <%
            if (!"All".equals(currentCategory)) {
        %>
        <option value="noteList.html?category=">All</option>
        <%
            }%>
        <%
            Set<String> categories = (Set<String>) request.getAttribute("categories");
            if (categories != null && !categories.isEmpty()) {
                for (String category : categories) {
                    if (category.equals(currentCategory)) {
                        continue;
                    }
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
    <form id="createCategoryForm" action="manageCategory.html" method="post" style="display:none;">
        <label for="categoryName">Category Name:</label>
        <input type="text" id="categoryName" name="categoryName" required>
        <input type="hidden" name="action" value="createCategory">
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
            <%
                if (currentCategory == null || currentCategory.isEmpty() || !"All".equals(currentCategory)) {
            %>
            <form action="addRemoveNoteToCategory.html">
                <input type="hidden" name="noteId" value="<%=note.getId()%>">
                <input type="hidden" name="categoryName" value="<%=currentCategory%>">
                <input type="hidden" name="action" value="remove">
                <button type="submit">Remove From Current Category</button>
            </form>

            <%
            } else {
            %>
            <label for="addNoteToCategory">Add this note to category?</label>
            <select id="addNoteToCategory" name="categoryName" onchange="location = this.value;">
                <option value="noteList.html">No</option>
                <%
                    for (String category : categories) {
                        if (category.equals(currentCategory)) {
                            continue;
                        }
                        String link = "addRemoveNoteToCategory.html?noteId=" + note.getId() + "&categoryName=" + category + "&action=add";
                %>
                <option value="<%=link%>"><%=category%>
                </option>
                <%
                    }
                %>
            </select>
            <% } %>

        </li>
        <% } %>
    </ul>
    <form action="createNote.html" method="get">
        <input type="hidden" name="category" value="<%=currentCategory%>">
        <button type="submit">Create New Note</button>
    </form>
    <%
        if (!"All".equals(currentCategory)) {
    %>
    <form id="deleteCategoryForm" action="manageCategory.html" method="post">
        <input type="hidden" name="categoryName" value="<%=currentCategory%>">
        <input type="hidden" name="action" value="deleteCategory">
        <button type="submit">Delete Current Category</button>
    </form>
    <%
        }
    %>
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

    function showCreateCategoryForm() {
        var form = document.getElementById("createCategoryForm");
        form.style.display = "block";
    }
</script>
</body>
</html>
