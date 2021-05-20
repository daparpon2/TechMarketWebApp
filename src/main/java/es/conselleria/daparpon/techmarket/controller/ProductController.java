package es.conselleria.daparpon.techmarket.controller;

import es.conselleria.daparpon.techmarket.business.impl.ProductBusiness;
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
@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

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
        request.setAttribute("products", ProductBusiness.getInstance().all());

        RequestDispatcher rd;
        if (request.getSession().getAttribute("usertype") != null) {
            rd = request.getRequestDispatcher("/admin-products.jsp");
        } else {
            rd = request.getRequestDispatcher("/guest-products.jsp");
        }
        rd.forward(request, response);
    }
}
