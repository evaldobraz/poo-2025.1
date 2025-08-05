import entities.Product;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter product data:");
        System.out.print("Name: ");
        String productName;
        productName = sc.nextLine();

        System.out.print("Price: ");
        double productPrice;
        productPrice = sc.nextDouble();

        System.out.print("Quantity: ");
        int productQuantity;
        productQuantity = sc.nextInt();

        Product p =  new Product(productName, productPrice, productQuantity);

        System.out.println("Product data: "+p);

        System.out.print("Enter the number of products to be added in stock: ");
        productQuantity = sc.nextInt();
        p.addProduct(productQuantity);

        System.out.println("Updated data: "+p);


        System.out.print("Enter the number of products to be removed from stock: ");
        productQuantity = sc.nextInt();
        p.removeProduct(productQuantity);

        System.out.println("Updated data: "+p);
    }
}
