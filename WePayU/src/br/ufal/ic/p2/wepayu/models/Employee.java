package br.ufal.ic.p2.wepayu.models;

// import java.time.*;

public class Employee {
    private final int id;
    private static int nextId = 0;
    private final String name;
    private String address;
    private double salary;
    private String type;
    private boolean union;
    private double commission = 0;

    public Employee(String name, String adress, String type, double salary) throws IllegalArgumentException {
        if(salary < 0) throw new IllegalArgumentException("Salario deve ser nao-negativo.");
        if(type.equalsIgnoreCase("comissionado")) {
            throw new IllegalArgumentException("Tipo nao aplicavel.");
        }
        this.name = name;
        this.address = adress;
        this.type = type;
        this.salary = salary;
        this.union = false;
        id = nextId++;
    }

    public Employee(String name, String adress, String type, double salary, double commission) throws IllegalArgumentException {
        if(salary < 0) throw new IllegalArgumentException("Salario deve ser nao-negativo.");
        if(commission < 0) throw new IllegalArgumentException("Comissao deve ser nao-negativa.");
        if(type.equalsIgnoreCase("horista") || type.equalsIgnoreCase("assalariado")) {
            throw new IllegalArgumentException("Tipo nao aplicavel.");
        }
        this.name = name;
        this.address = adress;
        this.type = type;
        this.salary = salary;
        this.union = false;
        this.commission = commission;
        id = nextId++;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public double getSalary() {
        return this.salary;
    }

    public String getType() {
        return this.type;
    }

    public boolean isUnion() {
        return this.union;
    }

    public void setUnion(boolean union) {
        this.union = union;
    }

    public double getPayment(){
        return this.salary;
    };

    public double getCommission() {
        return commission;
    }

    public int getId() {
        return this.id;
    }

    public static void resetNextId() {
        nextId = 0;
    }

    @Override
    public String toString() {
        return String.format("FUNCIONÁRIO: %s, ID: %d", this.name, this.id);
    }

}
