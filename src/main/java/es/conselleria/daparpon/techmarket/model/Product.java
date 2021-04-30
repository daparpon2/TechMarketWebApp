/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.model;

/**
 * @author cesar.diaz
 */
public class Product {

    private Integer productId;
    private Manufacturer manufacturer;
    private ProductCode productCode;
    private Double purchaseCost;
    private Integer quantityOnHand;
    private Double markup;
    private Boolean available;
    private String description;

    public Product() {
        this.quantityOnHand = 0;
        this.available = false;
        this.markup = 0.0;
    }

    public Product(Integer productId, String description, Double purchaseCost, Integer quantityOnHand, Double markup) {
        this.productId = productId;
        this.description = description;
        this.purchaseCost = purchaseCost;
        this.quantityOnHand = quantityOnHand;
        this.markup = markup;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCode productCode) {
        this.productCode = productCode;
    }

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Double getMarkup() {
        return markup;
    }

    public void setMarkup(Double markup) {
        this.markup = markup;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
