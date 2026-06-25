package com.finpulse.m4_shortest_path;

import java.util.*;

public class BellmanFord {
    private int vertices;
    private List<int[]> edges; // [from, to, weight]

    public BellmanFord(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    // Add Edge
    public void addEdge(int from, int to, int weight) {
        edges.add(new int[]{from, to, weight});
    }

    // Bellman-Ford Algorithm
    public void bellmanFord(int src) {
        int[] dist = new int[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        // Relax all edges V-1 times
        for (int i = 1; i < vertices; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];
                if (dist[from] != Integer.MAX_VALUE &&
                        dist[from] + weight < dist[to]) {
                    dist[to] = dist[from] + weight;
                    parent[to] = from;
                }
            }
        }

        // Check for negative weight cycles (Arbitrage Detection)
        boolean arbitrage = false;
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            if (dist[from] != Integer.MAX_VALUE &&
                    dist[from] + weight < dist[to]) {
                arbitrage = true;
                break;
            }
        }

        displayResult(src, dist, parent, arbitrage);
    }

    // Display Result
    private void displayResult(int src, int[] dist, int[] parent, boolean arbitrage) {
        System.out.println("\n===== Bellman-Ford (from Account#" + src + ") =====");
        System.out.println("  Account\t| Min Cost\t| Path");
        System.out.println("  ------------------------------------------------");
        for (int i = 0; i < vertices; i++) {
            if (i != src) {
                System.out.print("  Account#" + i + "\t| ");
                if (dist[i] == Integer.MAX_VALUE)
                    System.out.print("Unreachable\t| N/A");
                else {
                    System.out.print("₹" + dist[i] + "\t\t| ");
                    printPath(parent, i);
                }
                System.out.println();
            }
        }
        System.out.println("  ------------------------------------------------");

        // Arbitrage Alert
        System.out.println("\n  ===== Arbitrage Detection =====");
        if (arbitrage)
            System.out.println("  ⚠️  ARBITRAGE OPPORTUNITY DETECTED! Negative cycle found!");
        else
            System.out.println("  ✓  No arbitrage opportunities detected.");
        System.out.println("  ================================");
        System.out.println("=================================================");
    }

    // Print Path
    private void printPath(int[] parent, int v) {
        if (parent[v] == -1) {
            System.out.print("Account#" + v);
            return;
        }
        printPath(parent, parent[v]);
        System.out.print(" → Account#" + v);
    }
}