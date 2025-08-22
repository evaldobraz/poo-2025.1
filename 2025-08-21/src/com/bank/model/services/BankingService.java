package com.bank.model.services;

import com.bank.model.entities.Account;
import com.bank.model.exceptions.InsufficientBalanceException;
import com.bank.model.exceptions.WithdrawLimitException;

public class BankingService {
    public BankingService() {}

    public void withdraw(double amount, Account account)
    {
        if(amount > account.getWithdrawLimit()) throw new WithdrawLimitException("The amount exceeds withdraw limit");
        if(account.getBalance() <= 0) throw new InsufficientBalanceException("Not enough balance");
        account.withdraw(amount);
    }
}
