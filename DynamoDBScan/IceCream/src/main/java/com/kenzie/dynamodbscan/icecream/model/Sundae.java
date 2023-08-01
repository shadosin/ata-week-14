package com.kenzie.dynamodbscan.icecream.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A sundae servable to ice cream shop customers.
 */
public class Sundae {

    private final List<Flavor> scoops = new ArrayList<>();
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * Add a scoop of the given flavor to the sundae.
     * @param flavor The flavor to add
     */
    public void addScoop(Flavor flavor) {
        if (scoops.isEmpty()) {
            price = price.add(new BigDecimal(3.50));
        } else {
            price = price.add(new BigDecimal(1.00));
        }
        scoops.add(flavor);
    }

    public List<Flavor> getScoops() {
        return new ArrayList<>(scoops);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sundae sundae = (Sundae) o;
        return getScoops().equals(sundae.getScoops());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScoops());
    }

    @Override
    public String toString() {
        return "Sundae{" +
                "scoops=" + scoops +
                '}';
    }
}
