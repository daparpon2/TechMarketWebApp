package es.conselleria.daparpon.techmarket.model;

/**
 *
 * @author Daniel Pardo Pont
 */
public class OrderStatus {
    private Integer statusCode;
    private String description;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
