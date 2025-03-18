package uk.ac.ucl.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;

@WebServlet("/uploadUrl.html")
public class UploadUrlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");
        String url = request.getParameter("url");
        NoteService noteService = NoteServiceFactory.getNoteService();
        Note note = noteService.getNoteById(noteId);
        note.appendImage(url, "url");

        response.sendRedirect("note.html?noteId=" + noteId);
    }
}
