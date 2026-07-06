package com.attila.model;

import com.attila.transaction.StockMovement;

import java.util.ArrayList;
import java.util.List;


public class Product {

    private Integer id;

    private Supplier supplier;

    private List<StockMovement> stockMovements;

    private Integer amount;

    public Product(Integer id, Supplier supplier, Integer amount) {
        this.id = id;
        this.supplier = supplier;
        this.amount = amount;
        this.stockMovements = new ArrayList<>();
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", supplier=" + supplier +
                ", stockMovements=" + stockMovements +
                ", amount=" + amount +
                '}';
    }
}
