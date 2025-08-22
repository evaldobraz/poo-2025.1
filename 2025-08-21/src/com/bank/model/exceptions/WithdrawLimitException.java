package com.bank.model.exceptions;

public class WithdrawLimitException extends RuntimeException {
    public WithdrawLimitException(String mensagem) {
        super(mensagem);
    }
}
