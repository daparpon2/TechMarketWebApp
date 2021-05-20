package es.conselleria.daparpon.techmarket.model;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public class DiscountCode {

    private Character discountCode;
    private Double rate;

    public Character getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(Character discountCode) {
        this.discountCode = discountCode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.valueOf(this.discountCode);
    }
}
