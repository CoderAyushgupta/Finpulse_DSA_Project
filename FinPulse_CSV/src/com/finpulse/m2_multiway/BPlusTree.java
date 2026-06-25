package com.finpulse.m2_multiway;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree {
    private static final int ORDER = 4;

    private class Node {
        List<Double> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        Node next;
        boolean isLeaf;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }
    }

    private Node root;

    public BPlusTree() {
        root = new Node(true);
    }

    // Insert key
    public void insert(double key) {
        Node r = root;
        if (r.keys.size() == ORDER - 1) {
            Node newRoot = new Node(false);
            newRoot.children.add(root);
            splitChild(newRoot, 0, root);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(Node node, double key) {
        if (node.isLeaf) {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) i++;
            node.keys.add(i, key);
        } else {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) i++;
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
        int mid = (ORDER - 1) / 2;

        newNode.keys.addAll(child.keys.subList(mid + 1, child.keys.size()));
        child.keys.subList(mid + 1, child.keys.size()).clear();

        if (!child.isLeaf) {
            newNode.children.addAll(child.children.subList(mid + 1, child.children.size()));
            child.children.subList(mid + 1, child.children.size()).clear();
        } else {
            newNode.next = child.next;
            child.next = newNode;
        }

        parent.keys.add(i, child.keys.get(mid));
        parent.children.add(i + 1, newNode);

        if (child.isLeaf) {
            child.keys.remove(mid);
        }
    }

    // Search key
    public boolean search(double key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node node, double key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) i++;
        if (node.isLeaf) {
            return i < node.keys.size() && node.keys.get(i) == key;
        }
        return searchRec(node.children.get(i), key);
    }

    // Range Query
    public void rangeQuery(double minAmount, double maxAmount) {
        System.out.println("\n===== B+ Tree Range Query =====");
        System.out.println("  Transactions between " + minAmount + " and " + maxAmount + ":");
        rangeQueryRec(root, minAmount, maxAmount);
        System.out.println("================================");
    }

    private void rangeQueryRec(Node node, double min, double max) {
        if (node.isLeaf) {
            for (double key : node.keys) {
                if (key >= min && key <= max)
                    System.out.println("  Amount: " + key);
            }
            if (node.next != null) rangeQueryRec(node.next, min, max);
        } else {
            int i = 0;
            while (i < node.keys.size() && min > node.keys.get(i)) i++;
            rangeQueryRec(node.children.get(i), min, max);
        }
    }

    // Display all leaf nodes
    public void display() {
        System.out.println("\n===== B+ Tree Transaction Index =====");
        Node curr = root;
        while (!curr.isLeaf) curr = curr.children.get(0);
        while (curr != null) {
            for (double key : curr.keys)
                System.out.print("  " + key + " |");
            curr = curr.next;
        }
        System.out.println("\n=====================================");
    }
}