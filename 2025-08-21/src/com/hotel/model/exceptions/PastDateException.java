package com.hotel.model.exceptions;

public class PastDateException extends RuntimeException {
    public PastDateException(String message) {
        super(message);
    }
}
