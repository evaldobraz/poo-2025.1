import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        TaxCollector tc = new TaxCollector(input);


        System.out.print("Enter the number of tax payers: ");
        int n = input.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println("Tax payer # " + i + " data");
            tc.addTaxpayer();
        }

        tc.getTaxesPaid();
        System.out.println(tc);
    }
}