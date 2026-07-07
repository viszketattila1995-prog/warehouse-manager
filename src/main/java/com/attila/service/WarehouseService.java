package com.attila.service;

import com.attila.exception.*;
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
                .orElseThrow(() -> new NoSupplierWithNameException("No supplier found with name: " + name));
    }

    public Product findProductById(Integer id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotExsistsException("This product doesn't exists"));
    }

    public Boolean existsById(Integer id) {
        return productList.stream()
                .anyMatch(p -> p.getId().equals(id));
    }

    public void addProduct(String supplierName, Integer id, String type, Integer extra) {
        Supplier supplier = findSupplierByName(supplierName);
        Integer amount = 0;

        if (existsById(id)){
            throw new ProductAlreadyExistsException("This product already exists with id: " + id);
        }

        switch (type){
            case "perishable" -> productList.add(new PerishableProduct(id, supplier, amount, extra));
            case "preorderable" -> productList.add(new PreorderableProduct(id, supplier, amount, extra));
            case "normal" -> productList.add(new Product(id, supplier, amount));
            default -> throw new TypeNotFoundException("No such type to be found");
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

    public void ship (Integer id, Integer amount) {
        Product product = findProductById(id);

        product.ship(amount);

        StockMovement stockMovement = new StockMovement(
                MovementType.SHIP,
                amount,
                product.getId(),
                null,
                "shipped",
                product.getAmount()
        );

        product.addMovement(stockMovement);
    }

    public void transfer(Integer source, Integer goal, Integer amount) {
        if (source.equals(goal)) {
            throw new SameProductTransferException("Source and goal cannot be the same");
        }

        Product sourceProduct = findProductById(source);
        Product goalProduct = findProductById(goal);

        Integer lossInPercent = amount * 5 / 100;

        if (!sourceProduct.isShippable(amount + lossInPercent)){
            throw new StockExceededException("The amount exceeded the available quantity");
        }

        sourceProduct.ship(amount);
        StockMovement transferOut = new StockMovement(
                MovementType.TRANSFER_OUT,
                amount,
                source,
                goal,
                "Transfer out",
                sourceProduct.getAmount()
        );
        sourceProduct.addMovement(transferOut);

        goalProduct.receive(amount);
        StockMovement transferIn = new StockMovement(
                MovementType.TRANSFER_IN,
                amount,
                source,
                goal,
                "Transfer in",
                goalProduct.getAmount()
        );
        goalProduct.addMovement(transferIn);

        if (lossInPercent > 0) {
            sourceProduct.ship(lossInPercent);
            StockMovement loss = new StockMovement(
                    MovementType.LOSS,
                    lossInPercent,
                    source,
                    goal,
                    "loss",
                    sourceProduct.getAmount()
            );
            sourceProduct.addMovement(loss);
        }
    }

    public List<StockMovement> getStockMovementHistory(Integer id) {
        Product product = findProductById(id);

        return product.getStockMovements();
    }
}
