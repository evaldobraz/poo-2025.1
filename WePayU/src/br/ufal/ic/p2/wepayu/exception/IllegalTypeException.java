package br.ufal.ic.p2.wepayu.exception;

public class IllegalTypeException extends RuntimeException {
    public IllegalTypeException() {
        super("Tipo invalido.");
    }
}
