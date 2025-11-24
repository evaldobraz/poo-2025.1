package br.ufal.ic.p2.wepayu.exception;

public class IllegalAttributeException extends RuntimeException {
    public IllegalAttributeException() {
        super("Atributo nao existe.");
    }
}
