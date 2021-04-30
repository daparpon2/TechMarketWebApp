/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.model;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * @author Cesardl
 */
public class Customer {

    private Integer customerId;

    @Valid
    @NotNull
    private DiscountCode discountCode;

    @Valid
    @NotNull
    private MicroMarket microMarket;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Size(min = 1, max = 30)
    private String addressLine1;

    @Size(max = 30)
    private String addressLine2;

    @NotNull
    @Size(min = 1, max = 25)
    private String city;

    @NotNull
    @Size(max = 2)
    private String state;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    @Size(max = 12)
    private String phone;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    @Size(max = 12)
    private String fax;

    @NotNull
    //@Email
    @Size(max = 40)
    private String email;

    @NotNull
    @Min(value = 1000)
    private Long creditLimit;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public MicroMarket getMicroMarket() {
        return microMarket;
    }

    public void setMicroMarket(MicroMarket microMarket) {
        this.microMarket = microMarket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
