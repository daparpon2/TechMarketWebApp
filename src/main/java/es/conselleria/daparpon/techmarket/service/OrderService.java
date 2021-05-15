/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.conselleria.daparpon.techmarket.business.impl.ProductBusiness;
import es.conselleria.daparpon.techmarket.business.impl.PurchaseOrderBusiness;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel Pardo Pont
 */
@WebServlet(name = "OrderService", urlPatterns = {"/order-service"})
public class OrderService extends HttpServlet {

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
        response.setContentType("application/json");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        PrintWriter out = response.getWriter();

        if (request.getParameter("num") != null) {
            out.print(gson.toJson(PurchaseOrderBusiness.getInstance().findOrderProducts(Integer.parseInt(request.getParameter("num")))));
        } else if (request.getParameter("selector") != null) {
            switch (request.getParameter("selector").toUpperCase()) {
                case "CUSTOMER":
                    out.print(gson.toJson(PurchaseOrderBusiness.getInstance().getCustomers()));
                    break;
                case "FREIGHT":
                    out.print(gson.toJson(PurchaseOrderBusiness.getInstance().getFreightCompanies()));
                    break;
                case "STATUS":
                    out.print(gson.toJson(PurchaseOrderBusiness.getInstance().getStatus()));
                    break;
                default:
            }
        } else {
            //DO NOTHING
        }
    }
}
