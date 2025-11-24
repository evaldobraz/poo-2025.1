package br.ufal.ic.p2.wepayu.model;

abstract public class Paycheck {

    private Employee employee = null;
    private double gross = 0.0;
    private double deductions = 0.0;
    private double netAmount = 0.0;

    public Paycheck(){}

    public Paycheck(Employee employee, double gross, double deductions, double netAmount) {
        this.employee = employee;
        this.gross = gross;
        this.deductions = deductions;
        this.netAmount = netAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public String getName() {
        return employee.getName();
    }


    public double getGross() {
        return gross;
    }

    public double getDeductions() {
        return deductions;
    }

    public String getPaymentMethod() {

        String paymentMethod = employee.getPaymentMethod().toString();

        if(paymentMethod.equals("Correios")) {
            paymentMethod = String.format("Correios, "+getAddress());
        }

        return paymentMethod;
    }

    public String getAddress() {
        return employee.getAddress();
    }
}