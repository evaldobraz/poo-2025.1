import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxCollector {
    private List<Taxpayer> taxpayersList = new ArrayList<>();
    private Scanner input;

    public TaxCollector(Scanner input) {
        this.input = input;
    }

    public void addTaxpayer(){
        String taxpayerType, taxpayerName;
        double annualIncome;
        System.out.print("Individual or company (i/c)? ");

        taxpayerType = input.next();
        System.out.print("Name: ");
        taxpayerName = input.next();

        System.out.print("Annual income: ");
        annualIncome = input.nextDouble();

        Taxpayer newTaxpayer = null;

        if(taxpayerType.equalsIgnoreCase("i")) {
            double healthExpenses;

            System.out.print("Health expenditures: ");
            healthExpenses = input.nextDouble();

            newTaxpayer = new Individual(taxpayerName, annualIncome, healthExpenses);

        } else if(taxpayerType.equalsIgnoreCase("c")) {
            int employees;

            System.out.print("Employees: ");
            employees = input.nextInt();

            newTaxpayer = new Company(taxpayerName, annualIncome, employees);
        }
        if(newTaxpayer != null) this.taxpayersList.add(newTaxpayer);
    }

    public double getTotalTaxes() {
        double totalTaxes = 0;
        //percorre todos os elementos de uma lista
        for (Taxpayer taxpayer : taxpayersList){
            totalTaxes += taxpayer.getTaxes();
        }
        return totalTaxes;
    }

    public void getTaxesPaid() {
        System.out.println("TAXES PAID:");
        for (Taxpayer taxpayer : taxpayersList){
            System.out.println(taxpayer.toString());
        }
    }

    @Override
    public String toString() {
        return String.format("\nTOTAL TAXES: $ %.2f\n", getTotalTaxes());
    }
}
