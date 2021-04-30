package es.conselleria.daparpon.techmarket.model;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class Manufacturer {

    private Integer manufacturerId;
    private String name;
    private String email;

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.email;
    }
}
