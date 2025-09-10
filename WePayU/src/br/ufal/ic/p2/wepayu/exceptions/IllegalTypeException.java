package br.ufal.ic.p2.wepayu.exceptions;

public class IllegalTypeException extends RuntimeException {
    public IllegalTypeException() {
        super("Tipo invalido.");
    }
}
