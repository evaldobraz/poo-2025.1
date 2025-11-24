package br.ufal.ic.p2.wepayu.model;

public class BankAccountDeposit extends PaymentMethod {
    private String bankName;
    private String agencyNumber;
    private String accountNumber;

    public BankAccountDeposit() {}

    public BankAccountDeposit(String bankName, String agencyNumber, String accountNumber) {
        this.bankName = bankName;
        this.agencyNumber = agencyNumber;
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }

    public void setAgencyNumber(String agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return String.format("%s, Ag. %s CC %s", bankName, agencyNumber, accountNumber);
    }
}
