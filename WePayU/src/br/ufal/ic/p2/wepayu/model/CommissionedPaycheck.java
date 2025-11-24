package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.service.NumberService;

public class CommissionedPaycheck extends Paycheck{
    private double fixed;
    private double sales;
    private double commission;

    public CommissionedPaycheck(){}

    public CommissionedPaycheck(Commissioned commissioned, double gross, double deductions, double netAmount, double fixed, double sales, double commission) {
        super(commissioned, gross, deductions, netAmount);
        this.fixed = fixed;
        this.sales = sales;
        this.commission = commission;
    }


    public double getFixed() {
        return fixed;
    }

    public double getSales() {
        return sales;
    }

    public double getCommission() {
        return commission;
    }

    @Override
    public String toString() {
        final String DATA_FORMAT = "%-21s %8s %8s %8s %13s %9s %15s %s";
        return String.format(
                DATA_FORMAT,
                super.getName(),
                NumberService.formatAsCurrency(getFixed()),
                NumberService.formatAsCurrency(getSales()),
                NumberService.formatAsCurrency(getCommission()),
                NumberService.formatAsCurrency(super.getGross()),
                NumberService.formatAsCurrency(super.getDeductions()),
                NumberService.formatAsCurrency(super.getNetAmount()),
                super.getPaymentMethod()
        );
    }


}
