package br.ufal.ic.p2.wepayu.exception;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String message) {
        super("Data" + message + "invalida.");
    }
}
