package com.attila.exception;

public class ProductNotExsistsException extends RuntimeException {
    public ProductNotExsistsException(String message) {
        super(message);
    }
}
