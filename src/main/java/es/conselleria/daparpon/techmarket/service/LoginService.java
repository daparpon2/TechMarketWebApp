/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.service;

import es.conselleria.daparpon.techmarket.business.impl.UserBusiness;
import es.conselleria.daparpon.techmarket.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yo mismo
 */
@WebServlet(name = "LoginService", urlPatterns = {"/login"})
public class LoginService extends HttpServlet {

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
        String email = request.getParameter("email");
        String password = request.getParameter("pwd");

        User loginUser = UserBusiness.getInstance().findById(email);

        //JsonObjectBuilder jsonBuilder;
        String jsonOutput;

        if (loginUser == null) {
            jsonOutput = "\"login\":\"false\",\"cause\":\"error\"";
            /*
            jsonBuilder = Json.createObjectBuilder()
                    .add("login", "false")
                    .add("cause", "error");
             */
        } else {
            if (loginUser.getEmail() == null) {
                jsonOutput = "\"login\":\"false\",\"cause\":\"username\"";
            /*
            jsonBuilder = Json.createObjectBuilder()
                    .add("login", "false")
                    .add("cause", "username");
             */
            } else {
                if (password.equals(loginUser.getPassword())) {
                    jsonOutput = "\"login\":\"true\"";
                    /*
                jsonBuilder = Json.createObjectBuilder()
                        .add("login", "true");
                     */

                    if (loginUser.isAdmin()) {
                        request.getSession().setAttribute("usertype", "admin");
                        request.getSession().setAttribute("username", "Admin");
                        jsonOutput += ",\"usertype\":\"admin\"";
                        //jsonBuilder.add("usertype", "admin");
                    } else {
                        // GET CUSTOMER NAME
                        // ADD CUSTOMER ATTRIBUTES TO SESSION
                        //jsonBuilder.add("usertype", "customer");
                        jsonOutput += ",\"usertype\":\"customer\"";
                    }
                } else {
                    jsonOutput = "\"login\":\"false\",\"cause\":\"password\"";
                    /*
                jsonBuilder = Json.createObjectBuilder()
                        .add("login", "false")
                        .add("cause", "password");
                     */
                }
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        //JsonObject jsonObject = jsonBuilder.build();
        Writer writer = new StringWriter();
        //Json.createWriter(writer).write(jsonObject);
        PrintWriter out = response.getWriter();
        //out.print(writer.toString());
        out.print("{" + jsonOutput + "}");
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

}
