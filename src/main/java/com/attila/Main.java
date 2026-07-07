package com.attila;


import com.attila.engine.WarehouseEngine;
import com.attila.ui.UI;

public class Main {
    public static void main(String[] args) {

        UI ui = new UI();
        WarehouseEngine warehouseEngine = new WarehouseEngine(ui);
        warehouseEngine.run();
    }
}