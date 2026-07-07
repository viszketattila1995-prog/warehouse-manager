package com.attila.engine;

import com.attila.service.WarehouseService;
import com.attila.transaction.StockMovement;
import com.attila.ui.UI;

public class WarehouseEngine {

    private final UI ui;

    private final WarehouseService warehouseService;

    public WarehouseEngine(UI ui) {
        this.ui = ui;
        this.warehouseService = new WarehouseService();
    }

    public void run() {

        boolean isRunning = true;

        while (isRunning) {
            String command = ui.read();
            String[] commands = command.split("\\s++" + "\\|" + "\\s++");
            String[] params = new String[0];
            if (commands.length > 1) {
                params = commands[1].split("\\s++");
            }

            try {
                switch (commands[0]) {
                    case "registerSupplier" -> warehouseService.registerNewSupplier(params[0]);
                    case "addProduct" ->
                            warehouseService.addProduct(params[0], Integer.parseInt(params[1]), params[2], params.length > 3 ? Integer.parseInt(params[3]) : null);
                    case "receive" ->
                            warehouseService.receive(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                    case "ship" -> warehouseService.ship(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                    case "transfer" ->
                            warehouseService.transfer(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
                    case "history" -> printHistory((Integer.parseInt(params[0])));
                    case "quit" -> isRunning = false;
                    default -> throw new IllegalArgumentException("Not a valid statement");
                }
            } catch (RuntimeException e) {
                ui.printMessage("Error: " + e.getMessage());
            }
        }
        ui.close();
    }

    private void printHistory(Integer id) {
        for (StockMovement stockMovement : warehouseService.getStockMovementHistory(id)) {
            ui.printMessage(stockMovement.toString());
        }
    }
}
