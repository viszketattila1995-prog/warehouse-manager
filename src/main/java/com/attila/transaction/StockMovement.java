package com.attila.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockMovement {

    private MovementType movementType;

    private Integer amount;

    private Integer source;

    private Integer goal;

    private String description;

    private Integer stocksAfterTheMove;

    private LocalDateTime timeStamp;

    private Long id;

    private static Long nextId = 1L;

    public StockMovement() {
    }

    public StockMovement(MovementType movementType, Integer amount, Integer source, Integer goal, String description, Integer stocksAfterTheMove) {
        this.movementType = movementType;
        this.amount = amount;
        this.source = source;
        this.goal = goal;
        this.description = description;
        this.stocksAfterTheMove = stocksAfterTheMove;
        this.timeStamp = LocalDateTime.now();
        this.id = nextId;
        nextId++;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStocksAfterTheMove() {
        return stocksAfterTheMove;
    }

    public void setStocksAfterTheMove(Integer stocksAfterTheMove) {
        this.stocksAfterTheMove = stocksAfterTheMove;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        String time = timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String route = switch (movementType) {
            case RECEIVE -> "→ #" + goal;
            case SHIP -> "#" + source + " →";
            case TRANSFER_OUT, TRANSFER_IN, LOSS -> "#" + source + " → #" + goal;
        };
        return String.format("[%s] #%d  %-12s %5d db  %s   (stock: %d)",
                time, id, movementType, amount, route, stocksAfterTheMove);
    }
}