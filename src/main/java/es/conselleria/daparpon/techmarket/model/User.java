/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.model;

/**
 *
 * @author Yo mismo
 */
public class User {
    private String email;
    private String password;
    private boolean isAdmin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        if(isAdmin) {
            return "Admin: " + email;
        } else {
            return "User: " + email;
        }
    }
}
