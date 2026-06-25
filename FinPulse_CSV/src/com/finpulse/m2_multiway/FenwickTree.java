package com.finpulse.m2_multiway;

public class FenwickTree {
    private double[] tree;
    private int n;

    public FenwickTree(int n) {
        this.n = n;
        tree = new double[n + 1];
    }

    // Update - Add value at index
    public void update(int index, double value) {
        index++; // 1-based index
        while (index <= n) {
            tree[index] += value;
            index += index & (-index);
        }
    }

    // Prefix Sum Query (0 to index)
    public double prefixSum(int index) {
        index++; // 1-based index
        double sum = 0;
        while (index > 0) {
            sum += tree[index];
            index -= index & (-index);
        }
        return sum;
    }

    // Range Sum Query (l to r)
    public double rangeSum(int l, int r) {
        if (l == 0) return prefixSum(r);
        return prefixSum(r) - prefixSum(l - 1);
    }

    // Display Cumulative Transactions
    public void displayCumulative(int index) {
        System.out.println("\n===== Cumulative Transaction Values =====");
        System.out.println("  Cumulative Sum (0 to " + index + "): " + prefixSum(index));
        System.out.println("=========================================");
    }

    // Display Range
    public void displayRange(int l, int r) {
        System.out.println("\n===== Range Transaction Query =====");
        System.out.println("  Sum from index " + l + " to " + r + ": " + rangeSum(l, r));
        System.out.println("===================================");
    }
}