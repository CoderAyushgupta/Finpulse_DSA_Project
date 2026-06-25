package com.finpulse.m6_greedy_dp;

import java.util.*;

public class FractionalKnapsack {

    private static class Investment {
        int id;
        String name;
        double value;  // expected return
        double weight; // capital required
        double ratio;  // value/weight

        Investment(int id, String name, double value, double weight) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.weight = weight;
            this.ratio = value / weight;
        }
    }

    private List<Investment> investments;

    public FractionalKnapsack() {
        investments = new ArrayList<>();
    }

    // Add Investment
    public void addInvestment(int id, String name, double value, double capital) {
        investments.add(new Investment(id, name, value, capital));
    }

    // Fractional Knapsack Algorithm
    public void optimizePortfolio(double totalCapital) {
        // Sort by ratio (value/weight) descending
        investments.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        double totalValue = 0;
        double remainingCapital = totalCapital;
        List<double[]> selected = new ArrayList<>(); // [id, fraction, value]

        for (Investment inv : investments) {
            if (remainingCapital <= 0) break;

            if (inv.weight <= remainingCapital) {
                // Take full investment
                selected.add(new double[]{inv.id, 1.0, inv.value});
                totalValue += inv.value;
                remainingCapital -= inv.weight;
            } else {
                // Take fraction
                double fraction = remainingCapital / inv.weight;
                selected.add(new double[]{inv.id, fraction, fraction * inv.value});
                totalValue += fraction * inv.value;
                remainingCapital = 0;
            }
        }

        displayResult(selected, totalCapital, totalValue);
    }

    // Display Result
    private void displayResult(List<double[]> selected, double totalCapital, double totalValue) {
        System.out.println("\n===== Fractional Knapsack - Investment Portfolio =====");
        System.out.println("  Total Capital Available: ₹" + totalCapital);
        System.out.println("  -------------------------------------------------------");
        System.out.printf("  %-4s| %-20s| %-10s| %-10s%n",
                "ID", "Investment", "Fraction", "Return");
        System.out.println("  -------------------------------------------------------");

        for (double[] s : selected) {
            Investment inv = investments.stream()
                    .filter(i -> i.id == (int) s[0])
                    .findFirst().orElse(null);
            if (inv != null) {
                System.out.printf("  %-4d| %-20s| %-10.2f| ₹%.2f%n",
                        inv.id, inv.name, s[1], s[2]);
            }
        }
        System.out.println("  -------------------------------------------------------");
        System.out.printf("  Total Expected Return: ₹%.2f%n", totalValue);
        System.out.println("  ✓ Optimal investment allocation achieved!");
        System.out.println("=====================================================");
    }

    // Display All Investments
    public void displayAll() {
        System.out.println("\n===== Available Investments =====");
        System.out.printf("  %-4s| %-20s| %-10s| %-10s| %-10s%n",
                "ID", "Name", "Return", "Capital", "Ratio");
        System.out.println("  -----------------------------------------------");
        for (Investment inv : investments) {
            System.out.printf("  %-4d| %-20s| ₹%-9.2f| ₹%-9.2f| %.2f%n",
                    inv.id, inv.name, inv.value, inv.weight, inv.ratio);
        }
        System.out.println("=================================");
    }
}