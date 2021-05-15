package es.conselleria.daparpon.techmarket.model;

import java.util.Date;

/**
 * Created on 17/06/2018.
 * 
 * Modified on 14/05/2021 by Daniel Pardo Pont
 *
 * @author Cesardl
 */
public class PurchaseOrder {

    private Integer orderNum;
    private Customer customer;
    private Double shippingCost;
    private Date salesDate;
    private Date shippingDate;
    private OrderStatus status;
    private FreightCompany freightCompany;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public FreightCompany getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(FreightCompany freightCompany) {
        this.freightCompany = freightCompany;
    }
}
