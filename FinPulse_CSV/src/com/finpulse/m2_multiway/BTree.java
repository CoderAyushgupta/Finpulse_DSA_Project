package com.finpulse.m2_multiway;

import java.util.ArrayList;
import java.util.List;

public class BTree {
    private static final int ORDER = 4;

    private class Node {
        List<Integer> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        boolean isLeaf;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }
    }

    private Node root;

    public BTree() {
        root = new Node(true);
    }

    // Search
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) i++;
        if (i < node.keys.size() && node.keys.get(i) == key) return true;
        if (node.isLeaf) return false;
        return searchRec(node.children.get(i), key);
    }

    // Insert
    public void insert(int key) {
        Node r = root;
        if (r.keys.size() == ORDER - 1) {
            Node newRoot = new Node(false);
            newRoot.children.add(root);
            splitChild(newRoot, 0, r);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(Node node, int key) {
        int i = node.keys.size() - 1;
        if (node.isLeaf) {
            node.keys.add(0);
            while (i >= 0 && key < node.keys.get(i)) {
                node.keys.set(i + 1, node.keys.get(i));
                i--;
            }
            node.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < node.keys.get(i)) i--;
            i++;
            Node child = node.children.get(i);
            if (child.keys.size() == ORDER - 1) {
                splitChild(node, i, child);
                if (key > node.keys.get(i)) i++;
            }
            insertNonFull(node.children.get(i), key);
        }
    }

    private void splitChild(Node parent, int i, Node child) {
        Node newNode = new Node(child.isLeaf);
        int mid = ORDER / 2 - 1;

        for (int j = mid + 1; j < child.keys.size(); j++)
            newNode.keys.add(child.keys.get(j));

        if (!child.isLeaf) {
            for (int j = mid + 1; j < child.children.size(); j++)
                newNode.children.add(child.children.get(j));
        }

        int midKey = child.keys.get(mid);

        child.keys.subList(mid, child.keys.size()).clear();
        if (!child.isLeaf)
            child.children.subList(mid + 1, child.children.size()).clear();

        parent.keys.add(i, midKey);
        parent.children.add(i + 1, newNode);
    }

    // Display
    public void display() {
        System.out.println("\n===== B-Tree Transaction ID Index =====");
        displayRec(root, 0);
        System.out.println("=======================================");
    }

    private void displayRec(Node node, int level) {
        System.out.print("  Level " + level + ": ");
        for (int key : node.keys)
            System.out.print("[" + key + "] ");
        System.out.println();
        if (!node.isLeaf)
            for (Node child : node.children)
                displayRec(child, level + 1);
    }

    // Search and Display Result
    public void searchAndDisplay(int transactionId) {
        System.out.println("\n===== B-Tree Search =====");
        boolean found = search(transactionId);
        System.out.println("  Transaction ID " + transactionId +
                (found ? " → FOUND ✓" : " → NOT FOUND ✗"));
        System.out.println("=========================");
    }
}