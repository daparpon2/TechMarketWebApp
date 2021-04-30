package es.conselleria.daparpon.techmarket.model;

/**
 * Created on 14/06/2018.
 *
 * @author Cesardl
 */
public class ProductCode {

    private String prodCode;
    private String description;

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
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
