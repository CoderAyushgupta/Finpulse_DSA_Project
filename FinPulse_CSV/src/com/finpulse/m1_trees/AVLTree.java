package com.finpulse.m1_trees;

import com.finpulse.models.Transaction;

public class AVLTree {
    private class Node {
        Transaction transaction;
        Node left, right;
        int height;

        Node(Transaction transaction) {
            this.transaction = transaction;
            height = 1;
        }
    }

    private Node root;

    // Get Height
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    // Get Balance Factor
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right Rotate
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    // Left Rotate
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // Insert Transaction
    public void insert(Transaction transaction) {
        root = insertRec(root, transaction);
    }

    private Node insertRec(Node node, Transaction transaction) {
        if (node == null) return new Node(transaction);

        if (transaction.getTransactionId() < node.transaction.getTransactionId())
            node.left = insertRec(node.left, transaction);
        else if (transaction.getTransactionId() > node.transaction.getTransactionId())
            node.right = insertRec(node.right, transaction);
        else
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && transaction.getTransactionId() < node.left.transaction.getTransactionId())
            return rightRotate(node);
        // RR Case
        if (balance < -1 && transaction.getTransactionId() > node.right.transaction.getTransactionId())
            return leftRotate(node);
        // LR Case
        if (balance > 1 && transaction.getTransactionId() > node.left.transaction.getTransactionId()) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL Case
        if (balance < -1 && transaction.getTransactionId() < node.right.transaction.getTransactionId()) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    // Search Transaction
    public Transaction search(int transactionId) {
        return searchRec(root, transactionId);
    }

    private Transaction searchRec(Node node, int transactionId) {
        if (node == null) return null;
        if (node.transaction.getTransactionId() == transactionId)
            return node.transaction;
        if (transactionId < node.transaction.getTransactionId())
            return searchRec(node.left, transactionId);
        return searchRec(node.right, transactionId);
    }

    // Inorder Traversal - Sorted Transaction Report
    public void inorder() {
        System.out.println("\n===== Transaction Report (Sorted by Transaction ID) =====");
        inorderRec(root);
        System.out.println("=========================================================");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println("  TxID#" + node.transaction.getTransactionId() +
                    " | From: " + node.transaction.getFromAccount() +
                    " | To: " + node.transaction.getToAccount() +
                    " | Amount: " + node.transaction.getAmount() +
                    " | Type: " + node.transaction.getType() +
                    " | Flagged: " + node.transaction.isFlagged());
            inorderRec(node.right);
        }
    }
}