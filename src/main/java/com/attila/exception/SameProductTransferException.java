package com.attila.exception;

public class SameProductTransferException extends RuntimeException {
    public SameProductTransferException(String message) {
        super(message);
    }
}
