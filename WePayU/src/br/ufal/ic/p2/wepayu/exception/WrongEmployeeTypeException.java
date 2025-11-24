package br.ufal.ic.p2.wepayu.exception;

public class WrongEmployeeTypeException extends RuntimeException {
    public WrongEmployeeTypeException(String message) {
        super(message);
    }
}
