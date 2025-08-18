public class Individual extends Taxpayer{
    private double healthExpenses;

    public Individual(String name, double annualIncome, double healthExpenses) {
        super(name, annualIncome);
        this.healthExpenses = healthExpenses;
    }

    public double getHealthExpenses() {
        return healthExpenses;
    }

    public double getTaxRate() {
        double annualIncome = getAnnualIncome();

        if (annualIncome <= 20000) return 0.15;
        return 0.25;
    }

    public double getTaxes() {
        return (getAnnualIncome() * getTaxRate()) - (getHealthExpenses() * 0.5);
    }
}