package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.exception.EmpregadoNaoExisteException;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private int salario;

    public Empregado(String nome, String endereco, String tipo, int salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public int getSalario() {
        return salario;
    }

}
