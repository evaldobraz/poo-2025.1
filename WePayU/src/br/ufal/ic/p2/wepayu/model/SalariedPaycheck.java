package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.service.NumberService;

public class SalariedPaycheck extends Paycheck {

    public SalariedPaycheck(){}

    public SalariedPaycheck(Employee employee, double gross, double deductions, double netAmount) {
        super(employee, gross, deductions, netAmount);
    }

    @Override
    public String toString() {
        final String DATA_FORMAT = "%-48s %13s %9s %15s %s";

        return String.format(
                DATA_FORMAT,
                super.getName(),
                NumberService.formatAsCurrency(super.getGross()),
                NumberService.formatAsCurrency(super.getDeductions()),
                NumberService.formatAsCurrency(super.getNetAmount()),
                super.getPaymentMethod()
        );
    }

}
