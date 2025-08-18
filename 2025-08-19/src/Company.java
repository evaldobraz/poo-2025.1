public class Company extends Taxpayer {
    private int employees;

    public Company(String name, double annualIncome, int employees) {
        super(name, annualIncome);
        this.employees = employees;
    }

    public double getTaxRate() {
        if (this.employees <= 10) return 0.16;
        return 0.14;
    }

    public double getTaxes() {
        return (getAnnualIncome() * getTaxRate());
    }
}
