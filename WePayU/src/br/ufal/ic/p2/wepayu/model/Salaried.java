package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.service.DateService;

import java.time.LocalDate;

import static br.ufal.ic.p2.wepayu.service.CalculatorService.calculateDeductions;
import static br.ufal.ic.p2.wepayu.service.CalculatorService.calculateGrossPayment;

public class Salaried extends Employee {

    public Salaried(){}

    public Salaried(String name, String address, String type, double salary) throws Exception {
        super(name, address, type, salary);
        this.setLastPayDay(LocalDate.of(2004, 12, 31));
        this.setPaymentScheduleFromString("mensal $");
    }

    public Salaried(int existingId, String name, String address, String type, double salary) throws Exception {
        super(existingId, name, address, type, salary);
        this.setLastPayDay(LocalDate.of(2004, 12, 31));
        this.setPaymentScheduleFromString("mensal $");
    }

    @Override
    public Paycheck calculatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();
        double[] grossResults = calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double deductions = calculateDeductions(this, lastPayDay, payDay);


        double netAmountBeforeZero = grossPayment - deductions;
        double netAmountFinal;

        if (netAmountBeforeZero < 0) {
            this.setUnionDebt(netAmountBeforeZero * -1);
            netAmountFinal = 0;

        } else {
            netAmountFinal = netAmountBeforeZero;

            this.resetUnionDebt();

        }
        this.setLastPayDay(payDay);

        return new SalariedPaycheck(this, grossPayment, deductions, netAmountFinal);
    }

    @Override
    public Paycheck simulatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();
        double[] grossResults = calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double deductions = calculateDeductions(this, lastPayDay, payDay);


        double netAmountBeforeZero = grossPayment - deductions;
        double netAmountFinal;

        if (netAmountBeforeZero < 0) {
            netAmountFinal = 0;

        } else {
            netAmountFinal = netAmountBeforeZero;
        }

        return new SalariedPaycheck(this, grossPayment, deductions, netAmountFinal);
    }


//    @Override
//    public boolean isPayday(LocalDate date) {
//        //assalariado recebem no ultimo dia util do mês
//        return DateService.isLastBussinessDayOfMonth(date);
//    }
}
