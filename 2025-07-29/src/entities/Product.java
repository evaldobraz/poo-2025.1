package entities;

public class Product{
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double totalValueInStock() {

        return this.price*this.quantity;
    }

    public void addProduct(int quantity) {

        this.quantity += quantity;
    }

    public void removeProduct(int quantity) {

        addProduct(-quantity);
    }

    @Override
    public String toString() { //serve para "transformar" uma classe em uma string
        return name
                + ", $ "
                + String.format("%.2f", price)
                + ", "
                + quantity
                + " units, Total: $ "
                + String.format("%.2f", totalValueInStock());
    }
}

