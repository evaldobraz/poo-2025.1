package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.exception.ComissaoNegativaException;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static br.ufal.ic.p2.wepayu.service.CalculatorService.calculateDeductions;
import static br.ufal.ic.p2.wepayu.service.CalculatorService.calculateGrossPayment;

public class Commissioned extends Employee {
    private double commission;
    private List<Sale> sales = new ArrayList<>();

    public Commissioned() { }

    public Commissioned(String name, String address, String type, double salary, double commission) throws Exception {
        super(name, address, type, salary);
        setCommission(commission);
        this.sales = new ArrayList<>();
        this.setLastPayDay(LocalDate.of(2004, 12, 31));
        this.setPaymentScheduleFromString("semanal 2 5");
    }

    public Commissioned(int existingId, String name, String address, String type, double salary, double commission) throws Exception {
        super(existingId, name, address, type, salary);
        setCommission(commission);
        this.sales = new ArrayList<>();
        this.setLastPayDay(LocalDate.of(2004, 12, 31));
        this.setPaymentScheduleFromString("semanal 2 5");
    }

    public double getCommission() {
        return commission;
    }
    public void setCommission(double commission) throws ComissaoNegativaException {
        if (commission < 0) throw new ComissaoNegativaException();
        this.commission = commission;
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public List<Sale> getSales() {
        return sales;
    }

    @Override
    public Paycheck calculatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();
        double[] grossResults = calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double fixed = grossResults[1];
        double sales = grossResults[2];
        double commission = grossResults[3];

        double deductions = calculateDeductions(this, lastPayDay, payDay);
        double netAmount = grossPayment - deductions;

        this.resetUnionDebt();

        if(netAmount < 0){
            this.setUnionDebt(netAmount*-1);
            deductions = 0;
            netAmount = 0;
        }

        this.setLastPayDay(payDay);
        return new CommissionedPaycheck(this, grossPayment, deductions, netAmount, fixed, sales, commission);
    }

    @Override
    public Paycheck simulatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();
        double[] grossResults = calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double fixed = grossResults[1];
        double sales = grossResults[2];
        double commission = grossResults[3];

        double deductions = calculateDeductions(this, lastPayDay, payDay);
        double netAmount = grossPayment - deductions;

        if(netAmount < 0){
            deductions = 0;
            netAmount = 0;
        }

        return new CommissionedPaycheck(this, grossPayment, deductions, netAmount, fixed, sales, commission);
    }

}
