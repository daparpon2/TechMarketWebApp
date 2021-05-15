package es.conselleria.daparpon.techmarket.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel Pardo Pont
 */
@WebServlet(name = "TestDB", urlPatterns = {"/testdb"})
public class TestDB extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String line;
        try {
            Connection conn = new DBConnection().getConnection();
            line = conn.toString();
            conn.close();
        } catch (SQLException ex) {
            line = ex.getMessage();
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DB Connection Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Database Connection Result</h1>");
            out.println(line);
            out.println("</body>");
            out.println("</html>");
        }
    }

}
