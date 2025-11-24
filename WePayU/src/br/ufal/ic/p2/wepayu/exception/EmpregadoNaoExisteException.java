package br.ufal.ic.p2.wepayu.exception;

public class EmpregadoNaoExisteException extends RuntimeException{
    public EmpregadoNaoExisteException(String message){
        super(message);
    }
}
