package com.bank.model.entities;

public class Account {
    private final int number;
    private final String holder;
    private double balance;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getWithdrawLimit() {
        return this.withdrawLimit;
    }


    public void deposit(double amount)
    {
        this.balance += amount;
    }

    public void withdraw(double amount)
    {
        this.balance -= amount;
        System.out.println("New balance: " + this.balance);
    }

}
