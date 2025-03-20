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
                <% } else if ("bold_text".equals(content.getContentType())) { %>
                <strong><%=content.getContent()%>
                </strong>
                <% } else if ("image".equals(content.getContentType())) { %>
                <img src="${pageContext.request.contextPath}/image?file=<%=content.getContent()%>" alt="Image content">
                <% } else if ("url".equals(content.getContentType())) { %>
                <a href="<%=content.getContent()%>" contenteditable="false"><%=content.getContent()%>
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
    <!-- url Upload form (hidden) -->
    <form id="urlUploadForm" action="uploadUrl.html" method="post" style="display:none;">
        <input type="text" name="url" id="url">
        <input type="hidden" name="noteId" value="<%=note.getId()%>">
        <input type="submit" value="Upload URL">
    </form>

    <button id="addTextButton" onclick="addNewContentDiv('text')">Add Text</button>
    <button id="addBoldTextButton" onclick="addNewContentDiv('bold_text')">Add Bold Text</button>
    <button id="addImageButton" onclick="showImageUploadForm()">Add Image</button>
    <button id="addUrlButton" onclick="showUrlUploadForm()">Add URL</button>
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
            if (contentType === "text" || contentType === "bold_text") {
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
                console.log(contentDivs[i].innerHTML);
                const match = contentDivs[i].innerHTML.match(/href="([^"]+)"/);
                console.log(match);
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
        // refresh the page after saving
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Refresh the page
                location.reload();
            }
        };

    }

    function addNewContentDiv(contentType) {
        let newContentDiv = document.createElement("div");
        newContentDiv.contentEditable = "true";
        newContentDiv.className = "noteContent";
        newContentDiv.setAttribute("data-content-type", contentType);
        newContentDiv.addEventListener("blur", saveNote);

        if (contentType === "bold_text") {
            newContentDiv.innerHTML = "<strong><br></strong>";
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

    function showUrlUploadForm() {
        let form = document.getElementById("urlUploadForm");
        form.style.display = "block";
    }

    document.getElementById("saveButton").addEventListener("click", saveNote);
    document.getElementById("noteTitle").addEventListener("blur", saveNote);
    let contentDivs = document.getElementsByClassName("noteContent");
    for (let i = 0; i < contentDivs.length; i++) {
        contentDivs[i].addEventListener("blur", saveNote);
    }
</script>
</body>
</html>
