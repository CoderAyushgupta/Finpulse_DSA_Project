package com.finpulse.m5_sorting;

import com.finpulse.models.Transaction;
import java.util.ArrayList;
import java.util.List;

public class RadixSort {

    // Sort Transactions by Transaction ID
    public void sortByTransactionId(List<Transaction> transactions) {
        if (transactions.isEmpty()) return;

        int max = getMaxId(transactions);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countingSortById(transactions, exp);
    }

    private int getMaxId(List<Transaction> transactions) {
        int max = transactions.get(0).getTransactionId();
        for (Transaction t : transactions)
            if (t.getTransactionId() > max)
                max = t.getTransactionId();
        return max;
    }

    private void countingSortById(List<Transaction> list, int exp) {
        int n = list.size();
        List<Transaction> output = new ArrayList<>(n);
        for (int i = 0; i < n; i++) output.add(null);

        int[] count = new int[10];

        // Count occurrences
        for (Transaction t : list)
            count[(t.getTransactionId() / exp) % 10]++;

        // Cumulative count
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build output
        for (int i = n - 1; i >= 0; i--) {
            int digit = (list.get(i).getTransactionId() / exp) % 10;
            output.set(count[digit] - 1, list.get(i));
            count[digit]--;
        }

        // Copy back
        for (int i = 0; i < n; i++)
            list.set(i, output.get(i));
    }

    // Sort by Amount (integer part)
    public void sortByAmount(List<Transaction> transactions) {
        if (transactions.isEmpty()) return;

        int max = getMaxAmount(transactions);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countingSortByAmount(transactions, exp);
    }

    private int getMaxAmount(List<Transaction> transactions) {
        int max = (int) transactions.get(0).getAmount();
        for (Transaction t : transactions)
            if ((int) t.getAmount() > max)
                max = (int) t.getAmount();
        return max;
    }

    private void countingSortByAmount(List<Transaction> list, int exp) {
        int n = list.size();
        List<Transaction> output = new ArrayList<>(n);
        for (int i = 0; i < n; i++) output.add(null);

        int[] count = new int[10];

        for (Transaction t : list)
            count[((int) t.getAmount() / exp) % 10]++;

        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; i--) {
            int digit = ((int) list.get(i).getAmount() / exp) % 10;
            output.set(count[digit] - 1, list.get(i));
            count[digit]--;
        }

        for (int i = 0; i < n; i++)
            list.set(i, output.get(i));
    }

    // Display
    public void display(List<Transaction> transactions) {
        System.out.println("\n===== Radix Sort - Transaction ID Index =====");
        for (Transaction t : transactions) {
            System.out.println("  TxID#" + t.getTransactionId() +
                    " | From: " + t.getFromAccount() +
                    " | To: " + t.getToAccount() +
                    " | Amount: ₹" + t.getAmount());
        }
        System.out.println("=============================================");
    }
}