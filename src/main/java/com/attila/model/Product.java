package com.attila.model;

import com.attila.exception.NegativeNumberException;
import com.attila.exception.NotEnoughStockException;
import com.attila.transaction.StockMovement;

import java.util.ArrayList;
import java.util.List;


public class Product {

    private final Integer id;

    private final Supplier supplier;

    private final List<StockMovement> stockMovements;

    private Integer amount;

    public Product(Integer id, Supplier supplier, Integer amount) {
        this.id = id;
        this.supplier = supplier;
        this.amount = amount;
        this.stockMovements = new ArrayList<>();
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

    public void addMovement(StockMovement stockMovement) {
        stockMovements.add(stockMovement);
    }

    public void receive(Integer amount) {
        if (amount <= 0) {
            throw new NegativeNumberException("Amount cannot be 0 or negative");
        }
        this.amount += amount;
    }

    public Boolean isShippable(Integer amount) {
        return amount <= this.amount;
    }

    public void ship(Integer amount) {
        if (amount <= 0) {
            throw new NegativeNumberException("Amount cannot be negative or null");
        }

        if (isShippable(amount)) {
            this.amount -= amount;
        } else {
            throw new NotEnoughStockException("We don't have enough stocks");
        }
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
