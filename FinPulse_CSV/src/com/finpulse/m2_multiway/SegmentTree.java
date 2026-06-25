package com.finpulse.m2_multiway;

public class SegmentTree {
    private double[] tree;
    private int n;

    public SegmentTree(double[] amounts) {
        n = amounts.length;
        tree = new double[4 * n];
        build(amounts, 0, 0, n - 1);
    }

    // Build Tree
    private void build(double[] amounts, int node, int start, int end) {
        if (start == end) {
            tree[node] = amounts[start];
        } else {
            int mid = (start + end) / 2;
            build(amounts, 2 * node + 1, start, mid);
            build(amounts, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    // Range Sum Query
    public double query(int l, int r) {
        return queryRec(0, 0, n - 1, l, r);
    }

    private double queryRec(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) / 2;
        return queryRec(2 * node + 1, start, mid, l, r) +
                queryRec(2 * node + 2, mid + 1, end, l, r);
    }

    // Update Value
    public void update(int index, double newAmount) {
        updateRec(0, 0, n - 1, index, newAmount);
    }

    private void updateRec(int node, int start, int end, int index, double newAmount) {
        if (start == end) {
            tree[node] = newAmount;
        } else {
            int mid = (start + end) / 2;
            if (index <= mid)
                updateRec(2 * node + 1, start, mid, index, newAmount);
            else
                updateRec(2 * node + 2, mid + 1, end, index, newAmount);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    // Display Analytics
    public void displayAnalytics(int l, int r) {
        System.out.println("\n===== Transaction Analytics =====");
        System.out.println("  Range: [" + l + " - " + r + "]");
        System.out.println("  Total Amount: " + query(l, r));
        System.out.println("=================================");
    }
}