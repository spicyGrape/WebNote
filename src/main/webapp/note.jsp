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
        <div>
            <div contenteditable="true" class="noteContent">
                <%=content.getContent()%>
            </div>
            <button class="delete-button" onclick="deleteContent(this)">Delete</button>
        </div>
        <% } %>
    </div>
    <button id="addContentButton" onclick="addNewContentDiv()">Add Content</button>
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
            formData += "&noteContent=" + encodeURIComponent(contentDivs[i].innerText);
        }

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "saveNote.html", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.send(formData);
    }

    function addNewContentDiv() {
        let newContentDiv = document.createElement("div");
        newContentDiv.contentEditable = "true";
        newContentDiv.className = "noteContent";
        newContentDiv.addEventListener("blur", saveNote);
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
        //location.reload();
    }


    document.getElementById("saveButton").addEventListener("click", saveNote);
    let contentDivs = document.getElementsByClassName("noteContent");
    for (let i = 0; i < contentDivs.length; i++) {
        contentDivs[i].addEventListener("blur", saveNote);
    }
</script>
</body>
</html>
