package com.bank.model;

import com.bank.model.entities.Account;
import com.bank.model.exceptions.InsufficientBalanceException;
import com.bank.model.exceptions.WithdrawLimitException;
import com.bank.model.services.BankingService;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        BankingService bankingService = new BankingService();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter account data:");
        System.out.print("Number: ");
        int number = input.nextInt();
        input.nextLine();

        System.out.print("Holder: ");
        String holder = input.next();
        input.nextLine();

        System.out.print("Inicial balance: ");
        double balance = input.nextDouble();
        input.nextLine();

        System.out.print("Withdraw limit: ");
        double withdrawLimit = input.nextDouble();
        input.nextLine();

        Account account = new Account(number, holder, balance, withdrawLimit);

        try{
            System.out.print("Enter amount for withdraw: ");
            bankingService.withdraw(input.nextDouble(), account);
        }catch(InsufficientBalanceException | WithdrawLimitException e){
            System.err.print("Withdraw error: " + e.getMessage());
        }

        input.close();
    }
}
