package com.finpulse.utils;

public class DisplayHelper {

    // Main Banner
    public static void showBanner() {
        System.out.println("\n");
        System.out.println("  ███████╗██╗███╗   ██╗██████╗ ██╗   ██╗██╗     ███████╗███████╗");
        System.out.println("  ██╔════╝██║████╗  ██║██╔══██╗██║   ██║██║     ██╔════╝██╔════╝");
        System.out.println("  █████╗  ██║██╔██╗ ██║██████╔╝██║   ██║██║     ███████╗█████╗  ");
        System.out.println("  ██╔══╝  ██║██║╚██╗██║██╔═══╝ ██║   ██║██║     ╚════██║██╔══╝  ");
        System.out.println("  ██║     ██║██║ ╚████║██║     ╚██████╔╝███████╗███████║███████╗");
        System.out.println("  ╚═╝     ╚═╝╚═╝  ╚═══╝╚═╝      ╚═════╝ ╚══════╝╚══════╝╚══════╝");
        System.out.println("        Intelligent Financial Fraud Detection & Banking Analytics");
        System.out.println("  ================================================================");
    }

    // Main Menu
    public static void showMainMenu() {
        System.out.println("\n  ================================================================");
        System.out.println("                        MAIN MENU                               ");
        System.out.println("  ================================================================");
        System.out.println("   1. Account Management          (BST + AVL Tree)");
        System.out.println("   2. Transaction Analytics       (B-Tree + B+ Tree + Segment + Fenwick)");
        System.out.println("   3. Fraud Detection Network     (Graph + BFS + DFS + MST)");
        System.out.println("   4. Transfer Path Optimizer     (Dijkstra + Bellman-Ford + Floyd)");
        System.out.println("   5. Sort & Rank Customers       (Merge + Quick + Heap + Radix)");
        System.out.println("   6. Investment Optimizer        (Greedy + DP + Knapsack)");
        System.out.println("   7. CSV Data Reports        (Accounts / Transactions / Search / Audit Log)");
        System.out.println("   0. Exit");
        System.out.println("  ================================================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M1
    public static void showM1Menu() {
        System.out.println("\n  ================================");
        System.out.println("     Account Management (M1)     ");
        System.out.println("  ================================");
        System.out.println("   1. Add Account (BST)");
        System.out.println("   2. Search Account (BST)");
        System.out.println("   3. Delete Account (BST)");
        System.out.println("   4. View All Accounts (BST)");
        System.out.println("   5. Add Transaction (AVL)");
        System.out.println("   6. Search Transaction (AVL)");
        System.out.println("   7. View All Transactions (AVL)");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M2
    public static void showM2Menu() {
        System.out.println("\n  ================================");
        System.out.println("   Transaction Analytics (M2)    ");
        System.out.println("  ================================");
        System.out.println("   1. B-Tree Insert & Search");
        System.out.println("   2. B+ Tree Range Query");
        System.out.println("   3. Segment Tree Range Sum");
        System.out.println("   4. Fenwick Tree Prefix Sum");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M3
    public static void showM3Menu() {
        System.out.println("\n  ================================");
        System.out.println("   Fraud Detection Network (M3)  ");
        System.out.println("  ================================");
        System.out.println("   1. View Transaction Network");
        System.out.println("   2. BFS Connectivity Analysis");
        System.out.println("   3. DFS Fraud Loop Detection");
        System.out.println("   4. Fraud Alert (Cycle Check)");
        System.out.println("   5. Minimum Spanning Tree");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M4
    public static void showM4Menu() {
        System.out.println("\n  ================================");
        System.out.println("   Transfer Path Optimizer (M4)  ");
        System.out.println("  ================================");
        System.out.println("   1. Dijkstra Shortest Path");
        System.out.println("   2. Bellman-Ford & Arbitrage");
        System.out.println("   3. Floyd-Warshall All Paths");
        System.out.println("   4. Topological Sort Settlement");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M5
    public static void showM5Menu() {
        System.out.println("\n  ================================");
        System.out.println("    Sort & Rank Customers (M5)   ");
        System.out.println("  ================================");
        System.out.println("   1. Merge Sort (Transaction History)");
        System.out.println("   2. Quick Sort (Credit Score Rank)");
        System.out.println("   3. Heap Sort  (High Value Alerts)");
        System.out.println("   4. Radix Sort (Transaction IDs)");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Sub Menu - M6
    public static void showM6Menu() {
        System.out.println("\n  ================================");
        System.out.println("    Investment Optimizer (M6)    ");
        System.out.println("  ================================");
        System.out.println("   1. Activity Selection (Audit)");
        System.out.println("   2. Fractional Knapsack");
        System.out.println("   3. 0/1 Knapsack Portfolio");
        System.out.println("   4. LCS Document Matching");
        System.out.println("   5. Edit Distance Verification");
        System.out.println("   0. Back");
        System.out.println("  ================================");
        System.out.print("   Enter your choice: ");
    }

    // Divider
    public static void divider() {
        System.out.println("  ================================================================");
    }

    // Success Message
    public static void success(String msg) {
        System.out.println("  ✓ SUCCESS: " + msg);
    }

    // Error Message
    public static void error(String msg) {
        System.out.println("  ✗ ERROR: " + msg);
    }

    // Info Message
    public static void info(String msg) {
        System.out.println("  ℹ INFO: " + msg);
    }
}