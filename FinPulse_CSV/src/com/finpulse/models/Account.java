package com.finpulse.models;

public class Account {
    private int accountNumber;
    private String holderName;
    private double balance;
    private String accountType; // SAVINGS / CURRENT
    private int creditScore;

    public Account(int accountNumber, String holderName, double balance, String accountType, int creditScore) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
        this.creditScore = creditScore;
    }

    // Getters
    public int getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public int getCreditScore() { return creditScore; }

    // Setters
    public void setBalance(double balance) { this.balance = balance; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", creditScore=" + creditScore +
                '}';
    }
}