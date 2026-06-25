package com.finpulse.m5_sorting;

import com.finpulse.models.Account;
import java.util.List;

public class QuickSort {

    // Sort Accounts by Credit Score
    public void sortByCreditScore(List<Account> accounts) {
        if (accounts.size() <= 1) return;
        quickSortCredit(accounts, 0, accounts.size() - 1);
    }

    private void quickSortCredit(List<Account> list, int low, int high) {
        if (low < high) {
            int pi = partitionCredit(list, low, high);
            quickSortCredit(list, low, pi - 1);
            quickSortCredit(list, pi + 1, high);
        }
    }

    private int partitionCredit(List<Account> list, int low, int high) {
        int pivot = list.get(high).getCreditScore();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getCreditScore() <= pivot) {
                i++;
                Account temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Account temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    // Sort Accounts by Balance
    public void sortByBalance(List<Account> accounts) {
        if (accounts.size() <= 1) return;
        quickSortBalance(accounts, 0, accounts.size() - 1);
    }

    private void quickSortBalance(List<Account> list, int low, int high) {
        if (low < high) {
            int pi = partitionBalance(list, low, high);
            quickSortBalance(list, low, pi - 1);
            quickSortBalance(list, pi + 1, high);
        }
    }

    private int partitionBalance(List<Account> list, int low, int high) {
        double pivot = list.get(high).getBalance();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getBalance() <= pivot) {
                i++;
                Account temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Account temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    // Display Sorted Accounts
    public void display(List<Account> accounts) {
        System.out.println("\n===== Quick Sort - Customer Rankings =====");
        for (Account a : accounts) {
            System.out.println("  Account#" + a.getAccountNumber() +
                    " | " + a.getHolderName() +
                    " | Balance: ₹" + a.getBalance() +
                    " | Credit Score: " + a.getCreditScore());
        }
        System.out.println("==========================================");
    }
}