package com.attila.service;

import com.attila.model.PerishableProduct;
import com.attila.model.PreorderableProduct;
import com.attila.model.Product;
import com.attila.model.Supplier;
import com.attila.transaction.MovementType;
import com.attila.transaction.StockMovement;

import java.util.ArrayList;
import java.util.List;

public class WarehouseService {

    List<Supplier> supplierList;

    List<Product> productList;

    public WarehouseService() {
        this.supplierList = new ArrayList<>();
        this.productList = new ArrayList<>();
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void registerNewSupplier(String name) {
        Supplier supplier = new Supplier(name);
        supplierList.add(supplier);
    }

    public Supplier findSupplierByName(String name) {
        return supplierList.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No supplier found with name: " + name));
    }

    public Product findProductById(Integer id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This product doesn't exists"));
    }

    public Boolean existsById(Integer id) {
        return productList.stream()
                .anyMatch(p -> p.getId().equals(id));
    }

    public void addProduct(String supplierName, Integer id, String type, Integer extra) {
        Supplier supplier = findSupplierByName(supplierName);
        Integer amount = 0;

        if (existsById(id)){
            throw new IllegalArgumentException("This product already exists with id: " + id);
        }

        switch (type){
            case "perishable" -> productList.add(new PerishableProduct(id, supplier, amount, extra));
            case "preorderable" -> productList.add(new PreorderableProduct(id, supplier, amount, extra));
            case "normal" -> productList.add(new Product(id, supplier, amount));
            default -> throw new IllegalArgumentException("No such type to be found");
        }
    }

    public void receive (Integer id, Integer amount) {
        Product product = findProductById(id);

        product.receive(amount);

        StockMovement stockMovement = new StockMovement(
                MovementType.RECEIVE,
                amount,
                null,
                product.getId(),
                "received",
                product.getAmount()
        );

        product.addMovement(stockMovement);
    }
}
