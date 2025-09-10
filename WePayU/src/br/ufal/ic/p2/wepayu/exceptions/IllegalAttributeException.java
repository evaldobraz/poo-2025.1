package br.ufal.ic.p2.wepayu.exceptions;

public class IllegalAttributeException extends RuntimeException {
    public IllegalAttributeException() {
        super("Atributo nao existe.");
    }
}
