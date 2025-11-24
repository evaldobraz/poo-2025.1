package br.ufal.ic.p2.wepayu.model;

import java.time.LocalDate;

public abstract class TransactionBase {
    private LocalDate date;
    private double value1 = 0;
    private double value2 = 0;

    public TransactionBase(){}

    public TransactionBase(LocalDate date, double value1, double value2) {
        this.date = date;
        this.value1 = value1;
        this.value2 = value2;
    }

    public TransactionBase(LocalDate date, double value1) {
        this.date = date;
        this.value1 = value1;
    }

    public LocalDate getDate() {
        return date;
    }
    public double getValue1() {
        return value1;
    }
    public double getValue2() {
        return value2;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }
    public void setValue2(double value2) {
        this.value2 = value2;
    }
}
