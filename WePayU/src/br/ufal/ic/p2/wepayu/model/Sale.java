package br.ufal.ic.p2.wepayu.model;

import java.time.LocalDate;

public class Sale extends TransactionBase{

    public Sale(){}

    public Sale(LocalDate date, double value, double commission) throws Exception {
        super(date ,value ,commission);

        if(value <= 0) {
            throw new Exception("Valor deve ser positivo.");
        }
    }

    public double getSaleValue() {
        return getValue1();
    }

    public double getCommission() {
        return getValue2();
    }

}
