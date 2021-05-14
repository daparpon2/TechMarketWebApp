/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.conselleria.daparpon.techmarket.business.impl.ProductBusiness;
import es.conselleria.daparpon.techmarket.model.Manufacturer;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.ProductCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yo mismo
 */
@WebServlet(name = "ProductService", urlPatterns = {"/product-service"})
public class ProductService extends HttpServlet {

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

        if (request.getParameter("id") != null) {
            out.print(gson.toJson(ProductBusiness.getInstance().findById(Integer.parseInt(request.getParameter("id")))));
        } else if (request.getParameter("selector") != null) {
            switch (request.getParameter("selector").toUpperCase()) {
                case "MANUFACTURER":
                    out.print(gson.toJson(ProductBusiness.getInstance().getManufacturers()));
                    break;
                case "CATEGORY":
                    out.print(gson.toJson(ProductBusiness.getInstance().getProductCodes()));
                    break;
                default:
            }
        } else {

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Product newProduct = parseJsonRequest(request);
        newProduct.setProductId(ProductBusiness.getInstance().save(newProduct));

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        if (newProduct.getProductId() != null) {
            out.print(gson.toJson(newProduct));
        } else {
            out.print(gson.toJson(null));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(ProductBusiness.getInstance().delete(Integer.parseInt(request.getParameter("id"))));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
        String body = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
        Map<String, String> parameterMap = processInputParameters(body);

        Product product = parseParameterMap(parameterMap);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(ProductBusiness.getInstance().update(product));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private Product parseJsonRequest(HttpServletRequest request) {
        Product product = new Product();
        product.setImage(request.getParameter("image"));
        product.setDescription(request.getParameter("description"));
        product.setPurchaseCost(Double.parseDouble(request.getParameter("purchaseCost")));
        product.setQuantityOnHand(Integer.parseInt(request.getParameter("quantityOnHand")));
        product.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
        product.setMarkup(Double.parseDouble(request.getParameter("markup")));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(Integer.parseInt(request.getParameter("manufacturerId")));
        product.setManufacturer(manufacturer);

        ProductCode productCode = new ProductCode();
        productCode.setProdCode(request.getParameter("prodCode"));
        product.setProductCode(productCode);

        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            product.setProductId(Integer.parseInt(request.getParameter("id")));
        }

        return product;
    }

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

}
