package com.finpulse.m1_trees;

import com.finpulse.models.Account;

public class BST {
    private class Node {
        Account account;
        Node left, right;

        Node(Account account) {
            this.account = account;
            left = right = null;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    // Insert Account
    public void insert(Account account) {
        root = insertRec(root, account);
    }

    private Node insertRec(Node root, Account account) {
        if (root == null) return new Node(account);
        if (account.getAccountNumber() < root.account.getAccountNumber())
            root.left = insertRec(root.left, account);
        else if (account.getAccountNumber() > root.account.getAccountNumber())
            root.right = insertRec(root.right, account);
        return root;
    }

    // Search Account
    public Account search(int accountNumber) {
        return searchRec(root, accountNumber);
    }

    private Account searchRec(Node root, int accountNumber) {
        if (root == null) return null;
        if (root.account.getAccountNumber() == accountNumber)
            return root.account;
        if (accountNumber < root.account.getAccountNumber())
            return searchRec(root.left, accountNumber);
        return searchRec(root.right, accountNumber);
    }

    // Delete Account
    public void delete(int accountNumber) {
        root = deleteRec(root, accountNumber);
    }

    private Node deleteRec(Node root, int accountNumber) {
        if (root == null) return null;
        if (accountNumber < root.account.getAccountNumber())
            root.left = deleteRec(root.left, accountNumber);
        else if (accountNumber > root.account.getAccountNumber())
            root.right = deleteRec(root.right, accountNumber);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            Node minNode = findMin(root.right);
            root.account = minNode.account;
            root.right = deleteRec(root.right, minNode.account.getAccountNumber());
        }
        return root;
    }

    private Node findMin(Node root) {
        while (root.left != null) root = root.left;
        return root;
    }

    // Inorder Traversal - Sorted Report
    public void inorder() {
        System.out.println("\n===== Account Report (Sorted by Account Number) =====");
        inorderRec(root);
        System.out.println("=====================================================");
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println("  Account#" + root.account.getAccountNumber() +
                    " | " + root.account.getHolderName() +
                    " | Balance: " + root.account.getBalance() +
                    " | Credit Score: " + root.account.getCreditScore());
            inorderRec(root.right);
        }
    }
}