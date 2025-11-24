package br.ufal.ic.p2.wepayu.model;

import java.time.*;

public class Timesheet extends TransactionBase {

    public Timesheet(){}

    Timesheet(LocalDate date, double workedHours) {
        super(date, 0, 0);
        calculateAndSetHours(workedHours);
    }

    public double getNormalHours() {
        return getValue1();
    }

    public double getExtraHours() {
        return getValue2();
    }

    public void calculateAndSetHours(double workedHours) {
        double extraHours = workedHours > 8 ? (workedHours-8) : 0;
        double normalHours = workedHours - extraHours;
        super.setValue1(normalHours);
        super.setValue2(extraHours);
    }
}
