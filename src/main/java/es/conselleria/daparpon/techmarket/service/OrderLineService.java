/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.conselleria.daparpon.techmarket.business.impl.OrderProductBusiness;
import es.conselleria.daparpon.techmarket.business.impl.PurchaseOrderBusiness;
import es.conselleria.daparpon.techmarket.model.OrderProduct;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yo mismo
 */
@WebServlet(name = "OrderLineService", urlPatterns = {"/line-service"})
public class OrderLineService extends HttpServlet {

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

        if (request.getParameter("productId") == null) {
            out.print(gson.toJson(PurchaseOrderBusiness.getInstance().findOrderProducts(Integer.parseInt(request.getParameter("num")))));
        } else {
            //DO NOTHING
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        OrderProduct newLine = parseJsonRequest(request);
        Integer[] result = OrderProductBusiness.getInstance().save(newLine);

        if (result != null && result[0] > 0) {
            out.print(gson.toJson(newLine));
        } else {
            out.print(gson.toJson(null));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    private OrderProduct parseJsonRequest(HttpServletRequest request) {
        OrderProduct line = new OrderProduct();
        line.setQuantity(Integer.parseInt(request.getParameter("quantity")));

        if (request.getParameter("num") != null && !request.getParameter("num").isEmpty()) {
            PurchaseOrder order = new PurchaseOrder();
            order.setOrderNum(Integer.parseInt(request.getParameter("num")));
            line.setOrder(order);
        }

        if (request.getParameter("productId") != null && !request.getParameter("productId").isEmpty()) {
            Product product = new Product();
            product.setProductId(Integer.parseInt(request.getParameter("productId")));
            product.setDescription(request.getParameter("productDescription"));
            line.setProduct(product);
        }

        return line;
    }
    
    private OrderProduct parseParameterMap(Map<String, String> parameterMap) {
        return null;
    }

}
