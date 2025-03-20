package uk.ac.ucl.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NoteService;
import uk.ac.ucl.model.NoteServiceFactory;

import java.io.IOException;

@WebServlet("/manageCategory.html")
public class ManageCategory extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        String categoryName = request.getParameter("categoryName");
        if ("createCategory".equals(action)) {
            // Create a new category
            NoteService noteService = NoteServiceFactory.getNoteService();
            noteService.createNamedCategory(categoryName);
        } else if ("deleteCategory".equals(action)) {
            // Delete an existing category
            NoteService noteService = NoteServiceFactory.getNoteService();
            noteService.deleteCategory(categoryName);
        }
        // Redirect back to the note list page
        response.sendRedirect("noteList.html");
    }
}
