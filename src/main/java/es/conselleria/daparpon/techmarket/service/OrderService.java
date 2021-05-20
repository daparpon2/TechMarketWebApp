/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.conselleria.daparpon.techmarket.business.impl.PurchaseOrderBusiness;
import es.conselleria.daparpon.techmarket.model.Customer;
import es.conselleria.daparpon.techmarket.model.FreightCompany;
import es.conselleria.daparpon.techmarket.model.OrderStatus;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import es.conselleria.daparpon.techmarket.utils.UtilityFunctions;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;
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
        builder.setDateFormat("MM/dd/yyyy");
        Gson gson = builder.create();
        PrintWriter out = response.getWriter();

        if (request.getParameter("num") != null) {
            out.print(gson.toJson(PurchaseOrderBusiness.getInstance().findById(Integer.parseInt(request.getParameter("num")))));
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("dd/MM/yyyy");
        Gson gson = builder.create();
        PrintWriter out = response.getWriter();

        try {
            PurchaseOrder newOrder = parseJsonRequest(request);
            newOrder.setOrderNum(PurchaseOrderBusiness.getInstance().save(newOrder));

            if (newOrder.getOrderNum() != null) {
                out.print(gson.toJson(newOrder));
            } else {
                out.print(gson.toJson(null));
            }
        } catch (ParseException ex) {
            out.print(gson.toJson(ex.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(PurchaseOrderBusiness.getInstance().delete(Integer.parseInt(request.getParameter("num"))));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
        String body = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
        Map<String, String> parameterMap = UtilityFunctions.processInputParameters(body);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            PurchaseOrder order = parseParameterMap(parameterMap);
            out.print(PurchaseOrderBusiness.getInstance().update(order));
        } catch (ParseException ex) {
            out.print(ex.getMessage());
        }
    }

    private PurchaseOrder parseJsonRequest(HttpServletRequest request) throws ParseException {
        PurchaseOrder order = new PurchaseOrder();
        order.setShippingCost(Double.parseDouble(request.getParameter("shippingCost")));
        order.setSalesDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("salesDate")));

        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        customer.setName(request.getParameter("customerName"));
        order.setCustomer(customer);

        OrderStatus status = new OrderStatus();
        status.setStatusCode(Integer.parseInt(request.getParameter("statusCode")));
        status.setDescription(request.getParameter("statusDescription"));
        order.setStatus(status);

        FreightCompany company = new FreightCompany();
        company.setCompanyId(Integer.parseInt(request.getParameter("freightCompanyId")));
        company.setName(request.getParameter("freightCompanyName"));
        order.setFreightCompany(company);

        if (request.getParameter("num") != null && !request.getParameter("num").isEmpty()) {
            order.setOrderNum(Integer.parseInt(request.getParameter("num")));
        }

        if (request.getParameter("shippingDate") != null && !request.getParameter("shippingDate").isEmpty()) {
            order.setShippingDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("shippingDate")));
        }

        return order;
    }

    private PurchaseOrder parseParameterMap(Map<String, String> parameterMap) throws ParseException {
        PurchaseOrder order = new PurchaseOrder();
        order.setShippingCost(Double.parseDouble(parameterMap.get("shippingCost")));
        order.setSalesDate(new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.get("salesDate")));

        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(parameterMap.get("customerId")));
        customer.setName(parameterMap.get("customerName"));
        order.setCustomer(customer);

        OrderStatus status = new OrderStatus();
        status.setStatusCode(Integer.parseInt(parameterMap.get("statusCode")));
        status.setDescription(parameterMap.get("statusDescription"));
        order.setStatus(status);

        FreightCompany company = new FreightCompany();
        company.setCompanyId(Integer.parseInt(parameterMap.get("freightCompanyId")));
        company.setName(parameterMap.get("freightCompanyName"));
        order.setFreightCompany(company);

        if (parameterMap.get("num") != null && !parameterMap.get("num").isEmpty()) {
            order.setOrderNum(Integer.parseInt(parameterMap.get("num")));
        }

        if (parameterMap.get("shippingDate") != null && !parameterMap.get("shippingDate").isEmpty()) {
            order.setShippingDate(new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.get("shippingDate")));
        }

        return order;
    }
}
