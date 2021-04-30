package es.conselleria.daparpon.techmarket.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public class MicroMarket {

    @NotNull
    @Size(max = 10)
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
