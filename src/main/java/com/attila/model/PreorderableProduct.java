package com.attila.model;

public class PreorderableProduct extends Product{

    private Integer overdraftLimit;

    public PreorderableProduct(Integer id, Supplier supplier, Integer amount, Integer overdraftLimit) {
        super(id, supplier, amount);
        this.overdraftLimit = overdraftLimit;
    }

    public Integer getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(Integer overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public Boolean isShippable(Integer amount) {
        return getAmount() - amount >= -overdraftLimit;
    }
}
