package com.finpulse.m5_sorting;

import com.finpulse.models.Transaction;
import java.util.List;

public class HeapSort {

    // Sort Transactions by Amount (High Priority First)
    public void sortByAmount(List<Transaction> transactions) {
        int n = transactions.size();

        // Build Max Heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(transactions, n, i);

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            Transaction temp = transactions.get(0);
            transactions.set(0, transactions.get(i));
            transactions.set(i, temp);
            heapify(transactions, i, 0);
        }
    }

    private void heapify(List<Transaction> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && list.get(left).getAmount() > list.get(largest).getAmount())
            largest = left;
        if (right < n && list.get(right).getAmount() > list.get(largest).getAmount())
            largest = right;

        if (largest != i) {
            Transaction temp = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, temp);
            heapify(list, n, largest);
        }
    }

    // Sort Flagged Alerts (Fraud Priority)
    public void sortFraudAlerts(List<Transaction> transactions) {
        int n = transactions.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapifyFraud(transactions, n, i);

        for (int i = n - 1; i > 0; i--) {
            Transaction temp = transactions.get(0);
            transactions.set(0, transactions.get(i));
            transactions.set(i, temp);
            heapifyFraud(transactions, i, 0);
        }
    }

    private void heapifyFraud(List<Transaction> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Flagged transactions get higher priority
        if (left < n) {
            boolean leftHigher = list.get(left).isFlagged() && !list.get(largest).isFlagged()
                    || (list.get(left).isFlagged() == list.get(largest).isFlagged()
                    && list.get(left).getAmount() > list.get(largest).getAmount());
            if (leftHigher) largest = left;
        }
        if (right < n) {
            boolean rightHigher = list.get(right).isFlagged() && !list.get(largest).isFlagged()
                    || (list.get(right).isFlagged() == list.get(largest).isFlagged()
                    && list.get(right).getAmount() > list.get(largest).getAmount());
            if (rightHigher) largest = right;
        }

        if (largest != i) {
            Transaction temp = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, temp);
            heapifyFraud(list, n, largest);
        }
    }

    // Display
    public void display(List<Transaction> transactions) {
        System.out.println("\n===== Heap Sort - High Value Financial Alerts =====");
        for (Transaction t : transactions) {
            System.out.println("  TxID#" + t.getTransactionId() +
                    " | Amount: ₹" + t.getAmount() +
                    " | Type: " + t.getType() +
                    " | " + (t.isFlagged() ? "⚠️ FLAGGED" : "✓ Clean"));
        }
        System.out.println("===================================================");
    }
}