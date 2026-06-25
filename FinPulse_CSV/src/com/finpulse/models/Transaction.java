package com.finpulse.models;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private String type; // CREDIT / DEBIT / TRANSFER
    private LocalDateTime timestamp;
    private boolean isFlagged; // fraud flag

    public Transaction(int transactionId, int fromAccount, int toAccount, double amount, String type) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.isFlagged = false;
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public int getFromAccount() { return fromAccount; }
    public int getToAccount() { return toAccount; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isFlagged() { return isFlagged; }

    // Setters
    public void setFlagged(boolean flagged) { isFlagged = flagged; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + transactionId +
                ", from=" + fromAccount +
                ", to=" + toAccount +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", time=" + timestamp +
                ", flagged=" + isFlagged +
                '}';
    }
}