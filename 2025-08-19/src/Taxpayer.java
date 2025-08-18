import java.util.Locale;

public abstract class Taxpayer {
    private String name;
    private double annualIncome;

    public Taxpayer(String name, double annualIncome) {
        this.name = name;
        this.annualIncome = annualIncome;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public abstract double getTaxRate();

    public abstract double getTaxes();

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: $ %.2f", this.name, getTaxes());
    }
}
