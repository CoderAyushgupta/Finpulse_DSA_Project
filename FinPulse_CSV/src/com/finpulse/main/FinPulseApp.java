package com.finpulse.main;

import com.finpulse.models.*;
import com.finpulse.m1_trees.*;
import com.finpulse.m2_multiway.*;
import com.finpulse.m3_graphs.*;
import com.finpulse.m4_shortest_path.*;
import com.finpulse.m5_sorting.*;
import com.finpulse.m6_greedy_dp.*;
import com.finpulse.utils.*;
import com.finpulse.database.DatabaseManager;

import java.util.*;

public class FinPulseApp {

    static Scanner sc = new Scanner(System.in);

    // M1
    static BST bst = new BST();
    static AVLTree avl = new AVLTree();

    // M2
    static BTree btree = new BTree();
    static BPlusTree bPlusTree = new BPlusTree();
    static double[] amounts = {1000, 2500, 3000, 500, 7500, 4200, 900, 6000};
    static SegmentTree segTree = new SegmentTree(amounts);
    static FenwickTree fenwick = new FenwickTree(8);

    // M3
    static TransactionGraph graph = new TransactionGraph(6);
    static KruskalMST mst = new KruskalMST(6);

    // M4
    static Dijkstra dijkstra = new Dijkstra(5);
    static BellmanFord bellman = new BellmanFord(5);
    static FloydWarshall floyd = new FloydWarshall(5);
    static TopologicalSort topo = new TopologicalSort(6);

    // M5
    static List<Transaction> transactions = new ArrayList<>();
    static List<Account> accounts = new ArrayList<>();
    static MergeSort mergeSort = new MergeSort();
    static QuickSort quickSort = new QuickSort();
    static HeapSort heapSort = new HeapSort();
    static RadixSort radixSort = new RadixSort();

    // M6
    static ActivitySelection actSel = new ActivitySelection();
    static FractionalKnapsack fracKnap = new FractionalKnapsack();
    static Knapsack01 knap01 = new Knapsack01();
    static LCSAndEditDistance lcsED = new LCSAndEditDistance();

    public static void main(String[] args) {
        // ── Initialise CSV persistence FIRST ──────────────────────────────
        CSVManager.init();

        // ── Load saved data from CSV (if any exists) ──────────────────────
        loadFromCSV();

        // ── Then load hard-coded sample data ──────────────────────────────
        loadSampleData();

        DisplayHelper.showBanner();

        int choice;
        do {
            DisplayHelper.showMainMenu();
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> m1Menu();
                case 2 -> m2Menu();
                case 3 -> m3Menu();
                case 4 -> m4Menu();
                case 5 -> m5Menu();
                case 6 -> m6Menu();
                case 7 -> csvReportsMenu();   // NEW: CSV Reports menu
                case 0 -> {
                    CSVManager.logActivity("SYSTEM", "SHUTDOWN", "User exited FinPulse");
                    System.out.println("\n  Goodbye! Thank you for using FinPulse!\n");
                }
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ==================== LOAD FROM CSV (startup) ====================
    static void loadFromCSV() {
        List<Account> saved = CSVManager.loadAccounts();
        for (Account a : saved) {
            bst.insert(a);
            accounts.add(a);
        }
        List<Transaction> savedTx = CSVManager.loadTransactions();
        for (Transaction t : savedTx) {
            avl.insert(t);
            transactions.add(t);
        }
        if (!saved.isEmpty() || !savedTx.isEmpty()) {
            DisplayHelper.success("Loaded " + saved.size() + " accounts and "
                + savedTx.size() + " transactions from CSV.");
        }
    }

    // ==================== SAMPLE DATA ====================
    static void loadSampleData() {
        // Only insert sample accounts if none were loaded from CSV
        if (accounts.isEmpty()) {
            insertAccount(new Account(1001, "Ayush Sharma",  50000, "SAVINGS",  750));
            insertAccount(new Account(1002, "Riya Patel",    75000, "CURRENT",  820));
            insertAccount(new Account(1003, "Arjun Verma",   30000, "SAVINGS",  690));
            insertAccount(new Account(1004, "Priya Singh",   90000, "CURRENT",  880));
            insertAccount(new Account(1005, "Rahul Kumar",   25000, "SAVINGS",  620));
        }

        if (transactions.isEmpty()) {
            Transaction t1 = new Transaction(101, 1001, 1002, 5000, "TRANSFER");
            Transaction t2 = new Transaction(102, 1002, 1003, 2500, "TRANSFER");
            Transaction t3 = new Transaction(103, 1003, 1001, 1000, "TRANSFER");
            Transaction t4 = new Transaction(104, 1004, 1005, 7500, "TRANSFER");
            Transaction t5 = new Transaction(105, 1005, 1001, 3000, "DEBIT");
            t3.setFlagged(true);
            t5.setFlagged(true);
            insertTransaction(t1); insertTransaction(t2); insertTransaction(t3);
            insertTransaction(t4); insertTransaction(t5);
        }

        // B-Tree & B+ Tree (always re-init – not persisted separately)
        btree.insert(101); btree.insert(102); btree.insert(103);
        btree.insert(104); btree.insert(105);
        bPlusTree.insert(1000); bPlusTree.insert(2500);
        bPlusTree.insert(5000); bPlusTree.insert(7500); bPlusTree.insert(3000);

        // Fenwick Tree
        for (int i = 0; i < amounts.length; i++) fenwick.update(i, amounts[i]);

        // Graph
        graph.addTransaction(0, 1, 5000); graph.addTransaction(1, 2, 2500);
        graph.addTransaction(2, 0, 1000); graph.addTransaction(3, 4, 7500);
        graph.addTransaction(4, 5, 3000);

        // MST
        mst.addEdge(0,1,5000); mst.addEdge(1,2,2500); mst.addEdge(0,2,6000);
        mst.addEdge(2,3,1500); mst.addEdge(3,4,4000); mst.addEdge(4,5,3000);

        // Dijkstra
        dijkstra.addEdge(0,1,10); dijkstra.addEdge(0,2,20);
        dijkstra.addEdge(1,3,15); dijkstra.addEdge(2,3,10); dijkstra.addEdge(3,4,5);

        // Bellman Ford
        bellman.addEdge(0,1,10); bellman.addEdge(0,2,20);
        bellman.addEdge(1,3,15); bellman.addEdge(2,3,10); bellman.addEdge(3,4,5);

        // Floyd Warshall
        floyd.addEdge(0,1,10); floyd.addEdge(1,2,15);
        floyd.addEdge(2,3,20); floyd.addEdge(0,3,50); floyd.addEdge(3,4,5);

        // Topological Sort
        topo.addEdge(0,1); topo.addEdge(0,2); topo.addEdge(1,3);
        topo.addEdge(2,3); topo.addEdge(3,4); topo.addEdge(4,5);

        // Activity Selection
        actSel.addActivity(1,"Tax Audit",0,3); actSel.addActivity(2,"Fraud Review",2,5);
        actSel.addActivity(3,"Compliance Check",4,7); actSel.addActivity(4,"Risk Assessment",6,9);
        actSel.addActivity(5,"Annual Audit",8,11);

        // Fractional Knapsack
        fracKnap.addInvestment(1,"Gold ETF",60,10); fracKnap.addInvestment(2,"Mutual Fund",100,20);
        fracKnap.addInvestment(3,"Fixed Deposit",120,30); fracKnap.addInvestment(4,"Stocks",80,15);

        // 0/1 Knapsack
        knap01.addAsset(1,"Real Estate",300,100); knap01.addAsset(2,"Crypto",200,60);
        knap01.addAsset(3,"Bonds",150,40); knap01.addAsset(4,"Commodities",100,30);
    }

    // ── Shared helpers so every insert goes to CSV too ─────────────────────
    static void insertAccount(Account a) {
        bst.insert(a);
        accounts.add(a);

        // Save to CSV
        CSVManager.saveAccount(a);

        // Save to PostgreSQL
        DatabaseManager.insertAccount(a);
    }

    static void insertTransaction(Transaction t) {
        avl.insert(t);
        transactions.add(t);
        CSVManager.saveTransaction(t);
    }

    // ==================== M1 MENU ====================
    static void m1Menu() {
        int ch;
        do {
            DisplayHelper.showM1Menu();
            ch = sc.nextInt();
            switch (ch) {
                // ── Add Account ──────────────────────────────────────────
                case 1 -> {
                    System.out.print("  Account No: ");    int an   = sc.nextInt();
                    System.out.print("  Name: ");          String nm = sc.next();
                    System.out.print("  Balance: ");       double bl = sc.nextDouble();
                    System.out.print("  Type (SAVINGS/CURRENT): ");String tp = sc.next().toUpperCase();
                    System.out.print("  Credit Score: ");  int cs   = sc.nextInt();
                    Account newAcc = new Account(an, nm, bl, tp, cs);
                    insertAccount(newAcc);
                    DisplayHelper.success("Account added and saved to CSV!");
                }
                // ── Search Account (BST) ─────────────────────────────────
                case 2 -> {
                    System.out.print("  Enter Account No to search: ");
                    int query = sc.nextInt();
                    long t0 = System.nanoTime();
                    Account a = bst.search(query);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    if (a != null) System.out.println("  Found: " + a);
                    else           DisplayHelper.error("Account not found!");
                    // Log search to CSV
                    CSVManager.logSearch("BST_ACCOUNT", String.valueOf(query), a != null, ms);
                    CSVManager.logActivity("M1", "SEARCH_ACCOUNT",
                        "Query=" + query + " Found=" + (a != null) + " Time=" + ms + "ms");
                }
                // ── Delete Account ───────────────────────────────────────
                case 3 -> {
                    System.out.print("  Enter Account No to delete: ");
                    int delNo = sc.nextInt();
                    bst.delete(delNo);
                    CSVManager.deleteAccount(delNo);
                    DisplayHelper.success("Account deleted and marked in CSV!");
                }
                // ── View All Accounts ────────────────────────────────────
                case 4 -> {
                    bst.inorder();
                    CSVManager.logActivity("M1", "VIEW_ALL_ACCOUNTS", "BST inorder traversal");
                }
                // ── Add Transaction (AVL) ────────────────────────────────
                case 5 -> {
                    System.out.print("  TxID: ");   int tid  = sc.nextInt();
                    System.out.print("  From: ");   int from = sc.nextInt();
                    System.out.print("  To: ");     int to   = sc.nextInt();
                    System.out.print("  Amount: "); double amt = sc.nextDouble();
                    System.out.print("  Type (CREDIT/DEBIT/TRANSFER): "); String tp = sc.next();
                    Transaction tx = new Transaction(tid, from, to, amt, tp);
                    insertTransaction(tx);
                    transactions.add(tx);                 // also in live list
                    DisplayHelper.success("Transaction added and saved to CSV!");
                }
                // ── Search Transaction (AVL) ─────────────────────────────
                case 6 -> {
                    System.out.print("  Enter Transaction ID: ");
                    int txQuery = sc.nextInt();
                    long t0 = System.nanoTime();
                    Transaction t = avl.search(txQuery);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    if (t != null) System.out.println("  Found: " + t);
                    else           DisplayHelper.error("Transaction not found!");
                    CSVManager.logSearch("AVL_TRANSACTION", String.valueOf(txQuery), t != null, ms);
                    CSVManager.logActivity("M1", "SEARCH_TRANSACTION",
                        "Query=" + txQuery + " Found=" + (t != null) + " Time=" + ms + "ms");
                }
                // ── View All Transactions ────────────────────────────────
                case 7 -> {
                    avl.inorder();
                    CSVManager.logActivity("M1", "VIEW_ALL_TRANSACTIONS", "AVL inorder traversal");
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== M2 MENU ====================
    static void m2Menu() {
        int ch;
        do {
            DisplayHelper.showM2Menu();
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    System.out.print("  Enter Transaction ID to insert: ");
                    int txid = sc.nextInt();
                    btree.insert(txid);
                    btree.display();
                    CSVManager.logActivity("M2", "BTREE_INSERT", "TxID=" + txid);
                }
                case 2 -> {
                    System.out.print("  Min Amount: "); double min = sc.nextDouble();
                    System.out.print("  Max Amount: "); double max = sc.nextDouble();
                    bPlusTree.rangeQuery(min, max);
                    CSVManager.logActivity("M2", "BPLUS_RANGE_QUERY",
                        "Min=" + min + " Max=" + max);
                }
                case 3 -> {
                    System.out.print("  Left index: ");  int l = sc.nextInt();
                    System.out.print("  Right index: "); int r = sc.nextInt();
                    segTree.displayAnalytics(l, r);
                    CSVManager.logActivity("M2", "SEGTREE_RANGE", "L=" + l + " R=" + r);
                }
                case 4 -> {
                    System.out.print("  Index for prefix sum: ");
                    int idx = sc.nextInt();
                    fenwick.displayCumulative(idx);
                    CSVManager.logActivity("M2", "FENWICK_PREFIX", "Index=" + idx);
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== M3 MENU ====================
    static void m3Menu() {
        int ch;
        do {
            DisplayHelper.showM3Menu();
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    graph.displayGraph();
                    CSVManager.logActivity("M3", "GRAPH_DISPLAY", "Transaction graph shown");
                }
                case 2 -> {
                    System.out.print("  Start Account: ");
                    int start = sc.nextInt();
                    long t0 = System.nanoTime();
                    graph.bfs(start);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    CSVManager.logSearch("BFS_GRAPH", "StartNode=" + start, true, ms);
                    CSVManager.logActivity("M3", "BFS_RUN",
                        "StartNode=" + start + " Time=" + ms + "ms");
                }
                case 3 -> {
                    System.out.print("  Start Account: ");
                    int start = sc.nextInt();
                    long t0 = System.nanoTime();
                    graph.dfs(start);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    CSVManager.logSearch("DFS_GRAPH", "StartNode=" + start, true, ms);
                    CSVManager.logActivity("M3", "DFS_RUN",
                        "StartNode=" + start + " Time=" + ms + "ms");
                }
                case 4 -> {
                    graph.fraudAlert();
                    CSVManager.logActivity("M3", "FRAUD_ALERT_RUN",
                        "Cycle detection executed on transaction graph");
                }
                case 5 -> {
                    mst.kruskalMST();
                    CSVManager.logActivity("M3", "KRUSKAL_MST", "Minimum spanning tree computed");
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== M4 MENU ====================
    static void m4Menu() {
        int ch;
        do {
            DisplayHelper.showM4Menu();
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    System.out.print("  Source Account: ");
                    int src = sc.nextInt();
                    long t0 = System.nanoTime();
                    dijkstra.dijkstra(src);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    CSVManager.logSearch("DIJKSTRA", "Src=" + src, true, ms);
                    CSVManager.logActivity("M4", "DIJKSTRA_RUN",
                        "Src=" + src + " Time=" + ms + "ms");
                }
                case 2 -> {
                    System.out.print("  Source Account: ");
                    int src = sc.nextInt();
                    long t0 = System.nanoTime();
                    bellman.bellmanFord(src);
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    CSVManager.logSearch("BELLMAN_FORD", "Src=" + src, true, ms);
                    CSVManager.logActivity("M4", "BELLMAN_FORD_RUN",
                        "Src=" + src + " Time=" + ms + "ms");
                }
                case 3 -> {
                    long t0 = System.nanoTime();
                    floyd.floydWarshall();
                    long ms = (System.nanoTime() - t0) / 1_000_000;
                    System.out.print("  From Account: "); int from = sc.nextInt();
                    System.out.print("  To Account: ");   int to   = sc.nextInt();
                    floyd.printPath(from, to);
                    CSVManager.logSearch("FLOYD_WARSHALL",
                        "From=" + from + " To=" + to, true, ms);
                    CSVManager.logActivity("M4", "FLOYD_RUN",
                        "From=" + from + " To=" + to + " Time=" + ms + "ms");
                }
                case 4 -> {
                    topo.displayGraph();
                    topo.topologicalSort();
                    CSVManager.logActivity("M4", "TOPO_SORT", "Topological sort executed");
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== M5 MENU ====================
    static void m5Menu() {
        int ch;
        do {
            DisplayHelper.showM5Menu();
            ch = sc.nextInt();
            List<Transaction> txCopy  = new ArrayList<>(transactions);
            List<Account>     accCopy = new ArrayList<>(accounts);
            switch (ch) {
                case 1 -> {
                    mergeSort.sortByAmount(txCopy);
                    mergeSort.display(txCopy);
                    CSVManager.logActivity("M5", "MERGE_SORT",
                        "Sorted " + txCopy.size() + " transactions by amount");
                }
                case 2 -> {
                    quickSort.sortByCreditScore(accCopy);
                    quickSort.display(accCopy);
                    CSVManager.logActivity("M5", "QUICK_SORT",
                        "Sorted " + accCopy.size() + " accounts by credit score");
                }
                case 3 -> {
                    heapSort.sortFraudAlerts(txCopy);
                    heapSort.display(txCopy);
                    CSVManager.logActivity("M5", "HEAP_SORT",
                        "Sorted " + txCopy.size() + " fraud alerts");
                }
                case 4 -> {
                    radixSort.sortByTransactionId(txCopy);
                    radixSort.display(txCopy);
                    CSVManager.logActivity("M5", "RADIX_SORT",
                        "Sorted " + txCopy.size() + " transactions by ID");
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== M6 MENU ====================
    static void m6Menu() {
        int ch;
        do {
            DisplayHelper.showM6Menu();
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    actSel.displayAll();
                    actSel.selectActivities();
                    CSVManager.logActivity("M6", "ACTIVITY_SELECTION", "Greedy activity selection run");
                }
                case 2 -> {
                    fracKnap.displayAll();
                    System.out.print("  Total Capital: ");
                    double cap = sc.nextDouble();
                    fracKnap.optimizePortfolio(cap);
                    CSVManager.logActivity("M6", "FRAC_KNAPSACK",
                        "Capital=" + cap + " Fractional knapsack optimized");
                }
                case 3 -> {
                    knap01.displayAll();
                    System.out.print("  Total Budget: ");
                    int budget = sc.nextInt();
                    knap01.optimizePortfolio(budget);
                    CSVManager.logActivity("M6", "KNAPSACK_01",
                        "Budget=" + budget + " 0/1 knapsack optimized");
                }
                case 4 -> {
                    System.out.print("  Document 1: "); String s1 = sc.next();
                    System.out.print("  Document 2: "); String s2 = sc.next();
                    lcsED.lcs(s1, s2);
                    CSVManager.logActivity("M6", "LCS",
                        "Doc1=" + s1 + " Doc2=" + s2);
                }
                case 5 -> {
                    System.out.print("  Document 1: "); String s1 = sc.next();
                    System.out.print("  Document 2: "); String s2 = sc.next();
                    lcsED.editDistance(s1, s2);
                    CSVManager.logActivity("M6", "EDIT_DISTANCE",
                        "Doc1=" + s1 + " Doc2=" + s2);
                }
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }

    // ==================== CSV REPORTS MENU (NEW) ====================
    static void csvReportsMenu() {
        int ch;
        do {
            System.out.println("\n  ╔══════════════════════════════════╗");
            System.out.println("  ║     CSV DATA REPORTS (M7)        ║");
            System.out.println("  ╚══════════════════════════════════╝");
            System.out.println("   1. View All Accounts (CSV)");
            System.out.println("   2. View All Transactions (CSV)");
            System.out.println("   3. View Search Log (CSV)");
            System.out.println("   4. View Activity / Audit Log (CSV)");
            System.out.println("   0. Back to Main Menu");
            System.out.print("   Enter your choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> CSVManager.printAllAccounts();
                case 2 -> CSVManager.printAllTransactions();
                case 3 -> CSVManager.printSearchLog();
                case 4 -> CSVManager.printActivityLog();
                case 0 -> DisplayHelper.info("Returning to Main Menu...");
                default -> DisplayHelper.error("Invalid choice!");
            }
        } while (ch != 0);
    }
}
