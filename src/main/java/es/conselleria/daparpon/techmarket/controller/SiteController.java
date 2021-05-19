package es.conselleria.daparpon.techmarket.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel Pardo Pont
 */
@WebServlet(name = "SiteController", urlPatterns = {"/site"})
public class SiteController extends HttpServlet {

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
        RequestDispatcher rd;
        if (request.getSession().getAttribute("usertype") != null) {
            rd = request.getRequestDispatcher("/admin.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }
        rd.forward(request, response);
    }
}
