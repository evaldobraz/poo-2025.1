package br.ufal.ic.p2.wepayu.model;

import java.time.LocalDate;

public class Fee extends TransactionBase{
    public Fee(){}
    public Fee(LocalDate date, double value) {
        super(date, value);
    }

    public double getFee() {
        return getValue1();
    }

}
