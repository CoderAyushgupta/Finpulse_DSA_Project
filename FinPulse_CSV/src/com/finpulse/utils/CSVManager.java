package com.finpulse.utils;

import com.finpulse.models.Account;
import com.finpulse.models.Transaction;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * FinPulse CSV Manager
 * -------------------------------------------------------------------
 * Handles ALL persistent storage via CSV files inside  data/csv/
 *
 *  File              | What is stored
 * -------------------|------------------------------------------------
 *  accounts.csv      | Every account (created / updated / deleted)
 *  transactions.csv  | Every transaction (added / deleted)
 *  search_log.csv    | Every search operation with timestamp
 *  activity_log.csv  | Every user action with timestamp (audit trail)
 * -------------------------------------------------------------------
 */
public class CSVManager {

    // ── Paths ────────────────────────────────────────────────────────────────
    private static final String DATA_DIR        = "data/csv/";
    private static final String ACCOUNTS_FILE   = DATA_DIR + "accounts.csv";
    private static final String TXNS_FILE       = DATA_DIR + "transactions.csv";
    private static final String SEARCH_LOG      = DATA_DIR + "search_log.csv";
    private static final String ACTIVITY_LOG    = DATA_DIR + "activity_log.csv";

    // ── Headers ──────────────────────────────────────────────────────────────
    private static final String ACCOUNT_HEADER  =
        "AccountNumber,HolderName,Balance,AccountType,CreditScore,CreatedAt,Status";
    private static final String TXN_HEADER      =
        "TransactionID,FromAccount,ToAccount,Amount,Type,Timestamp,IsFlagged,Status";
    private static final String SEARCH_HEADER   =
        "SearchTimestamp,SearchType,Query,ResultFound,TimeTakenMs";
    private static final String ACTIVITY_HEADER =
        "Timestamp,Module,Action,Details";

    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ── Initialise (call once at startup) ─────────────────────────────────────
    public static void init() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            ensureFile(ACCOUNTS_FILE,  ACCOUNT_HEADER);
            ensureFile(TXNS_FILE,      TXN_HEADER);
            ensureFile(SEARCH_LOG,     SEARCH_HEADER);
            ensureFile(ACTIVITY_LOG,   ACTIVITY_HEADER);
            logActivity("SYSTEM", "STARTUP", "FinPulse CSV persistence initialised");
            System.out.println("  [CSV] Data files ready in: " + Paths.get(DATA_DIR).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("  [CSV] ERROR initialising files: " + e.getMessage());
        }
    }

    private static void ensureFile(String path, String header) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println(header);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ACCOUNTS
    // ════════════════════════════════════════════════════════════════════════

    /** Save a new account to accounts.csv */
    public static void saveAccount(Account a) {
        String row = String.join(",",
            String.valueOf(a.getAccountNumber()),
            escape(a.getHolderName()),
            String.format("%.2f", a.getBalance()),
            a.getAccountType(),
            String.valueOf(a.getCreditScore()),
            now(),
            "ACTIVE"
        );
        appendRow(ACCOUNTS_FILE, row);
        logActivity("M1", "ACCOUNT_CREATED",
            "AccNo=" + a.getAccountNumber() + " Name=" + a.getHolderName() +
            " Balance=" + a.getBalance() + " Type=" + a.getAccountType());
    }

    /** Mark account as DELETED in CSV (soft delete) */
    public static void deleteAccount(int accountNumber) {
        updateAccountStatus(accountNumber, "DELETED");
        logActivity("M1", "ACCOUNT_DELETED", "AccNo=" + accountNumber);
    }

    /** Load all ACTIVE accounts from CSV */
    public static List<Account> loadAccounts() {
        List<Account> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] p = line.split(",", -1);
                if (p.length >= 7 && "ACTIVE".equals(p[6].trim())) {
                    try {
                        list.add(new Account(
                            Integer.parseInt(p[0].trim()),
                            unescape(p[1]),
                            Double.parseDouble(p[2].trim()),
                            p[3].trim(),
                            Integer.parseInt(p[4].trim())
                        ));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            System.err.println("  [CSV] Could not read accounts: " + e.getMessage());
        }
        logActivity("SYSTEM", "ACCOUNTS_LOADED", "Count=" + list.size());
        return list;
    }

    private static void updateAccountStatus(int accNo, String status) {
        rewriteFile(ACCOUNTS_FILE, ACCOUNT_HEADER, line -> {
            String[] p = line.split(",", -1);
            if (p.length >= 1 && p[0].trim().equals(String.valueOf(accNo))) {
                p[6] = status;
                return String.join(",", p);
            }
            return line;
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // TRANSACTIONS
    // ════════════════════════════════════════════════════════════════════════

    /** Save a new transaction to transactions.csv */
    public static void saveTransaction(Transaction t) {
        String row = String.join(",",
            String.valueOf(t.getTransactionId()),
            String.valueOf(t.getFromAccount()),
            String.valueOf(t.getToAccount()),
            String.format("%.2f", t.getAmount()),
            t.getType(),
            t.getTimestamp() != null ? t.getTimestamp().format(FMT) : now(),
            String.valueOf(t.isFlagged()),
            "ACTIVE"
        );
        appendRow(TXNS_FILE, row);
        logActivity("M1", "TRANSACTION_ADDED",
            "TxID=" + t.getTransactionId() +
            " From=" + t.getFromAccount() +
            " To=" + t.getToAccount() +
            " Amount=" + t.getAmount() +
            " Flagged=" + t.isFlagged());
    }

    /** Soft-delete a transaction */
    public static void deleteTransaction(int txId) {
        rewriteFile(TXNS_FILE, TXN_HEADER, line -> {
            String[] p = line.split(",", -1);
            if (p.length >= 1 && p[0].trim().equals(String.valueOf(txId))) {
                p[7] = "DELETED";
                return String.join(",", p);
            }
            return line;
        });
        logActivity("M1", "TRANSACTION_DELETED", "TxID=" + txId);
    }

    /** Load all ACTIVE transactions from CSV */
    public static List<Transaction> loadTransactions() {
        List<Transaction> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TXNS_FILE))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] p = line.split(",", -1);
                if (p.length >= 8 && "ACTIVE".equals(p[7].trim())) {
                    try {
                        Transaction t = new Transaction(
                            Integer.parseInt(p[0].trim()),
                            Integer.parseInt(p[1].trim()),
                            Integer.parseInt(p[2].trim()),
                            Double.parseDouble(p[3].trim()),
                            p[4].trim()
                        );
                        t.setFlagged(Boolean.parseBoolean(p[6].trim()));
                        list.add(t);
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            System.err.println("  [CSV] Could not read transactions: " + e.getMessage());
        }
        logActivity("SYSTEM", "TRANSACTIONS_LOADED", "Count=" + list.size());
        return list;
    }

    // ════════════════════════════════════════════════════════════════════════
    // SEARCH LOG
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Log a search operation.
     * @param searchType  e.g. "BST_ACCOUNT", "AVL_TRANSACTION"
     * @param query       what was searched (e.g. account number)
     * @param found       whether result was found
     * @param timeTakenMs how long the search took
     */
    public static void logSearch(String searchType, String query, boolean found, long timeTakenMs) {
        String row = String.join(",",
            now(),
            searchType,
            escape(query),
            String.valueOf(found),
            String.valueOf(timeTakenMs)
        );
        appendRow(SEARCH_LOG, row);
    }

    // ════════════════════════════════════════════════════════════════════════
    // ACTIVITY / AUDIT LOG
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Log any user action.
     * @param module  e.g. "M1", "M3", "SYSTEM"
     * @param action  e.g. "ACCOUNT_CREATED", "BFS_RUN"
     * @param details free-text details
     */
    public static void logActivity(String module, String action, String details) {
        String row = String.join(",",
            now(),
            module,
            action,
            escape(details)
        );
        appendRow(ACTIVITY_LOG, row);
    }

    // ════════════════════════════════════════════════════════════════════════
    // DISPLAY / REPORT helpers (for menu options)
    // ════════════════════════════════════════════════════════════════════════

    public static void printAllAccounts() {
        printFile(ACCOUNTS_FILE, "ACCOUNTS CSV");
    }

    public static void printAllTransactions() {
        printFile(TXNS_FILE, "TRANSACTIONS CSV");
    }

    public static void printSearchLog() {
        printFile(SEARCH_LOG, "SEARCH LOG CSV");
    }

    public static void printActivityLog() {
        printFile(ACTIVITY_LOG, "ACTIVITY / AUDIT LOG CSV");
    }

    private static void printFile(String path, String title) {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.printf ("  ║  %-52s  ║%n", "📊 " + title);
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line; int row = 0;
            while ((line = br.readLine()) != null) {
                if (row == 0) System.out.println("  " + line); // header
                else          System.out.println("  " + line);
                row++;
            }
            if (row <= 1) System.out.println("  (no data yet)");
        } catch (IOException e) {
            System.err.println("  [CSV] Cannot read " + path);
        }
        System.out.println();
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════════════════════════════════════

    private static void appendRow(String filePath, String row) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.println(row);
        } catch (IOException e) {
            System.err.println("  [CSV] Write error (" + filePath + "): " + e.getMessage());
        }
    }

    /** Rewrite a CSV file applying a transformation to each data row */
    private static void rewriteFile(String filePath, String header, java.util.function.UnaryOperator<String> transform) {
        File f = new File(filePath);
        File tmp = new File(filePath + ".tmp");
        try (
            BufferedReader br  = new BufferedReader(new FileReader(f));
            PrintWriter    pw  = new PrintWriter(new FileWriter(tmp))
        ) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { pw.println(line); first = false; continue; }
                pw.println(transform.apply(line));
            }
        } catch (IOException e) {
            System.err.println("  [CSV] Rewrite error: " + e.getMessage());
            return;
        }
        if (!tmp.renameTo(f)) {
            // fallback for systems where rename fails across filesystems
            try {
                Files.move(tmp.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("  [CSV] Could not rename temp file: " + e.getMessage());
            }
        }
    }

    private static String now() {
        return LocalDateTime.now().format(FMT);
    }

    /** Wrap field in quotes if it contains a comma */
    private static String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"")) return "\"" + s.replace("\"", "\"\"") + "\"";
        return s;
    }

    private static String unescape(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        return s;
    }
}
