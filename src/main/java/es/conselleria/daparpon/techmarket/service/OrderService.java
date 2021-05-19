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
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
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
            order.setOrderNum(Integer.parseInt(request.getParameter("id")));
        }

        if (request.getParameter("shippingDate") != null && !request.getParameter("shippingDate").isEmpty()) {
            order.setShippingDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("shippingDate")));
        }

        return order;
    }
    
    private PurchaseOrder parseParameterMap(Map<String, String> parameterMap) {
        return null;
    }

    /*
    private Map<String, String> processInputParameters(String body) {
        Map<String, String> result = new HashMap();
        String[] params = body.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=");
            result.put(keyValuePair[0], keyValuePair[1]);
        }
        return result;
    }

    private Product parseParameterMap(Map<String, String> parameterMap) {
        Product product = new Product();
        product.setImage(parameterMap.get("image"));
        product.setDescription(parameterMap.get("description").replace("+", " "));
        product.setPurchaseCost(Double.parseDouble(parameterMap.get("purchaseCost")));
        product.setQuantityOnHand(Integer.parseInt(parameterMap.get("quantityOnHand")));
        product.setAvailable(Boolean.parseBoolean(parameterMap.get("available")));
        product.setMarkup(Double.parseDouble(parameterMap.get("markup")));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(Integer.parseInt(parameterMap.get("manufacturerId")));
        product.setManufacturer(manufacturer);

        ProductCode productCode = new ProductCode();
        productCode.setProdCode(parameterMap.get("prodCode"));
        product.setProductCode(productCode);

        if (parameterMap.get("id") != null && !parameterMap.get("id").isEmpty()) {
            product.setProductId(Integer.parseInt(parameterMap.get("id")));
        }

        return product;
    }

    private Product parseParameterMap(Map<String, String> parameterMap) {
        Product product = new Product();
        product.setImage(parameterMap.get("image"));
        product.setDescription(parameterMap.get("description").replace("+", " "));
        product.setPurchaseCost(Double.parseDouble(parameterMap.get("purchaseCost")));
        product.setQuantityOnHand(Integer.parseInt(parameterMap.get("quantityOnHand")));
        product.setAvailable(Boolean.parseBoolean(parameterMap.get("available")));
        product.setMarkup(Double.parseDouble(parameterMap.get("markup")));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(Integer.parseInt(parameterMap.get("manufacturerId")));
        product.setManufacturer(manufacturer);

        ProductCode productCode = new ProductCode();
        productCode.setProdCode(parameterMap.get("prodCode"));
        product.setProductCode(productCode);

        if (parameterMap.get("id") != null && !parameterMap.get("id").isEmpty()) {
            product.setProductId(Integer.parseInt(parameterMap.get("id")));
        }

        return product;
    }
     */
}
