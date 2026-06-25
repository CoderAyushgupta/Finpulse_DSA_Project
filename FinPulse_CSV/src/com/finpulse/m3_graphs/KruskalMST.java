package com.finpulse.m3_graphs;

import java.util.*;

public class KruskalMST {
    private int vertices;
    private List<int[]> edges; // [from, to, weight]

    public KruskalMST(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    // Add Edge
    public void addEdge(int from, int to, int weight) {
        edges.add(new int[]{from, to, weight});
    }

    // Union-Find - Find
    private int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    // Union-Find - Union
    private void union(int[] parent, int[] rank, int x, int y) {
        int px = find(parent, x);
        int py = find(parent, y);
        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else { parent[py] = px; rank[px]++; }
    }

    // Kruskal's MST
    public void kruskalMST() {
        // Sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e[2]));

        int[] parent = new int[vertices];
        int[] rank = new int[vertices];
        for (int i = 0; i < vertices; i++) parent[i] = i;

        List<int[]> mst = new ArrayList<>();
        int totalCost = 0;

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            int px = find(parent, from);
            int py = find(parent, to);

            if (px != py) {
                mst.add(edge);
                totalCost += weight;
                union(parent, rank, px, py);
            }
        }

        // Display MST
        System.out.println("\n===== Minimum Spanning Tree (Kruskal) =====");
        System.out.println("  Minimal Financial Transaction Paths:");
        for (int[] edge : mst) {
            System.out.println("  Account#" + edge[0] +
                    " ──── Account#" + edge[1] +
                    "  (Cost: ₹" + edge[2] + ")");
        }
        System.out.println("  Total Minimum Cost: ₹" + totalCost);
        System.out.println("==========================================");
    }

    // Display All Edges
    public void displayEdges() {
        System.out.println("\n===== All Transaction Edges =====");
        for (int[] edge : edges) {
            System.out.println("  Account#" + edge[0] +
                    " → Account#" + edge[1] +
                    " | Amount: ₹" + edge[2]);
        }
        System.out.println("=================================");
    }
}