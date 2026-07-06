package com.attila.model;

public class PerishableProduct extends Product{

    private Integer shelfLifeDays;

    public PerishableProduct(Integer id, Supplier supplier, Integer amount, Integer shelfLifeDays) {
        super(id, supplier, amount);
        this.shelfLifeDays = shelfLifeDays;
    }

    public Integer getShelfLifeDays() {
        return shelfLifeDays;
    }

    public void setShelfLifeDays(Integer shelfLifeDays) {
        this.shelfLifeDays = shelfLifeDays;
    }
}
