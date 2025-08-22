package com.hotel.model.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String mensagem) {
        super(mensagem);
    }
}
