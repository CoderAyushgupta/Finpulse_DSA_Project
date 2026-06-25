package com.finpulse.m5_sorting;

import com.finpulse.models.Transaction;
import java.util.List;

public class MergeSort {

    // Sort by Amount
    public void sortByAmount(List<Transaction> transactions) {
        if (transactions.size() <= 1) return;
        mergeSortAmount(transactions, 0, transactions.size() - 1);
    }

    private void mergeSortAmount(List<Transaction> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortAmount(list, left, mid);
            mergeSortAmount(list, mid + 1, right);
            mergeAmount(list, left, mid, right);
        }
    }

    private void mergeAmount(List<Transaction> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Transaction[] L = new Transaction[n1];
        Transaction[] R = new Transaction[n2];

        for (int i = 0; i < n1; i++) L[i] = list.get(left + i);
        for (int j = 0; j < n2; j++) R[j] = list.get(mid + 1 + j);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getAmount() <= R[j].getAmount())
                list.set(k++, L[i++]);
            else
                list.set(k++, R[j++]);
        }
        while (i < n1) list.set(k++, L[i++]);
        while (j < n2) list.set(k++, R[j++]);
    }

    // Sort by Transaction ID
    public void sortByTransactionId(List<Transaction> transactions) {
        if (transactions.size() <= 1) return;
        mergeSortId(transactions, 0, transactions.size() - 1);
    }

    private void mergeSortId(List<Transaction> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortId(list, left, mid);
            mergeSortId(list, mid + 1, right);
            mergeId(list, left, mid, right);
        }
    }

    private void mergeId(List<Transaction> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Transaction[] L = new Transaction[n1];
        Transaction[] R = new Transaction[n2];

        for (int i = 0; i < n1; i++) L[i] = list.get(left + i);
        for (int j = 0; j < n2; j++) R[j] = list.get(mid + 1 + j);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getTransactionId() <= R[j].getTransactionId())
                list.set(k++, L[i++]);
            else
                list.set(k++, R[j++]);
        }
        while (i < n1) list.set(k++, L[i++]);
        while (j < n2) list.set(k++, R[j++]);
    }

    // Display Sorted Transactions
    public void display(List<Transaction> transactions) {
        System.out.println("\n===== Merge Sort - Transaction History =====");
        for (Transaction t : transactions) {
            System.out.println("  TxID#" + t.getTransactionId() +
                    " | Amount: ₹" + t.getAmount() +
                    " | Type: " + t.getType() +
                    " | Flagged: " + t.isFlagged());
        }
        System.out.println("============================================");
    }
}