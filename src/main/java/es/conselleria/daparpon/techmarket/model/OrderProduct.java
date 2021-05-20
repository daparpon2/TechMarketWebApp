package es.conselleria.daparpon.techmarket.model;

/**
 *
 * @author Daniel Pardo Pont
 */
public class OrderProduct {
    private PurchaseOrder order;
    private Product product;
    private Integer quantity;

    public PurchaseOrder getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
