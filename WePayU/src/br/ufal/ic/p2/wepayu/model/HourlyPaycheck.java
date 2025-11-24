package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.service.NumberService;

public class HourlyPaycheck extends Paycheck {
    int normalHours;
    int extraHours;

    public HourlyPaycheck(){}

    public HourlyPaycheck(Employee employee, double gross, double deductions, double netAmount, int normalHours, int extraHours) {
        super(employee, gross, deductions, netAmount);
        this.normalHours = normalHours;
        this.extraHours = extraHours;
    }

    public int getNormalHours() {
        return normalHours;
    }

    public int getExtraHours() {
        return extraHours;
    }

    @Override
    public String toString() {
        final String DATA_FORMAT = "%-36s %5d %5d %13s %9s %15s %s";

        return String.format(
                DATA_FORMAT,
                super.getName(),
                normalHours,
                extraHours,
                NumberService.formatAsCurrency(super.getGross()),
                NumberService.formatAsCurrency(super.getDeductions()),
                NumberService.formatAsCurrency(super.getNetAmount()),
                super.getPaymentMethod()
        );
    }
}
