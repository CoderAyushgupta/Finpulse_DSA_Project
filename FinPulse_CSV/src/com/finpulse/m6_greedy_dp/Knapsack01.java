package com.finpulse.m6_greedy_dp;

import java.util.*;

public class Knapsack01 {

    private static class Asset {
        int id;
        String name;
        int value;  // expected return
        int weight; // capital required

        Asset(int id, String name, int value, int weight) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.weight = weight;
        }
    }

    private List<Asset> assets;

    public Knapsack01() {
        assets = new ArrayList<>();
    }

    // Add Asset
    public void addAsset(int id, String name, int value, int capital) {
        assets.add(new Asset(id, name, value, capital));
    }

    // 0/1 Knapsack Algorithm
    public void optimizePortfolio(int totalBudget) {
        int n = assets.size();
        int[][] dp = new int[n + 1][totalBudget + 1];

        // Build DP table
        for (int i = 1; i <= n; i++) {
            Asset asset = assets.get(i - 1);
            for (int w = 0; w <= totalBudget; w++) {
                // Don't take asset
                dp[i][w] = dp[i - 1][w];
                // Take asset if possible
                if (asset.weight <= w) {
                    int withAsset = dp[i - 1][w - asset.weight] + asset.value;
                    if (withAsset > dp[i][w])
                        dp[i][w] = withAsset;
                }
            }
        }

        // Backtrack to find selected assets
        List<Asset> selected = new ArrayList<>();
        int w = totalBudget;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected.add(assets.get(i - 1));
                w -= assets.get(i - 1).weight;
            }
        }

        Collections.reverse(selected);
        displayResult(selected, totalBudget, dp[n][totalBudget]);
    }

    // Display Result
    private void displayResult(List<Asset> selected, int totalBudget, int maxValue) {
        System.out.println("\n===== 0/1 Knapsack - Portfolio Optimization =====");
        System.out.println("  Total Budget: ₹" + totalBudget);
        System.out.println("  -----------------------------------------------");
        System.out.printf("  %-4s| %-20s| %-10s| %-10s%n",
                "ID", "Asset", "Return", "Capital");
        System.out.println("  -----------------------------------------------");

        int usedBudget = 0;
        for (Asset a : selected) {
            System.out.printf("  %-4d| %-20s| ₹%-9d| ₹%d%n",
                    a.id, a.name, a.value, a.weight);
            usedBudget += a.weight;
        }

        System.out.println("  -----------------------------------------------");
        System.out.println("  Assets Selected : " + selected.size());
        System.out.println("  Budget Used     : ₹" + usedBudget);
        System.out.println("  Budget Remaining: ₹" + (totalBudget - usedBudget));
        System.out.println("  Max Return      : ₹" + maxValue);
        System.out.println("  ✓ Optimal portfolio under budget constraints!");
        System.out.println("=================================================");
    }

    // Display All Assets
    public void displayAll() {
        System.out.println("\n===== Available Assets =====");
        System.out.printf("  %-4s| %-20s| %-10s| %-10s%n",
                "ID", "Asset Name", "Return", "Capital");
        System.out.println("  ----------------------------------------");
        for (Asset a : assets) {
            System.out.printf("  %-4d| %-20s| ₹%-9d| ₹%d%n",
                    a.id, a.name, a.value, a.weight);
        }
        System.out.println("============================");
    }
}