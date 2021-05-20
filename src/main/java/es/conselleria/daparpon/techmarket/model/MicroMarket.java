package es.conselleria.daparpon.techmarket.model;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public class MicroMarket {
    
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return this.zipCode;
    }
}
