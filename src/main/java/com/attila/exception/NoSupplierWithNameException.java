package com.attila.exception;

public class NoSupplierWithNameException extends RuntimeException {
    public NoSupplierWithNameException(String message) {
        super(message);
    }
}
