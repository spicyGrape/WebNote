package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;

@WebServlet("/addRemoveNoteToCategory.html")
public class AddRemoveNoteToCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Retrieve parameters from the request
        String noteId = request.getParameter("noteId");
        String categoryName = request.getParameter("categoryName");
        String action = request.getParameter("action");

        if (categoryName != null && !"All".equals(categoryName)) {
            NoteService noteService = NoteServiceFactory.getNoteService();
            if ("add".equals(action)) {
                // Add the note to the category
                noteService.addNoteToCategory(noteId, categoryName);
            } else if ("remove".equals(action)) {
                // Remove the note from the category
                noteService.removeNoteFromCategory(noteId, categoryName);
            }
        }

        // Redirect back to the note list page
        try {
            String redirectUrl = request.getContextPath() + "/noteList.html";
            if (categoryName != null && !"All".equals(categoryName)) {
                redirectUrl += "?category=" + categoryName;
            }
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
