package com.finpulse.m4_shortest_path;

public class FloydWarshall {
    private int vertices;
    private int[][] dist;
    private int[][] next;
    private static final int INF = Integer.MAX_VALUE / 2;

    public FloydWarshall(int vertices) {
        this.vertices = vertices;
        dist = new int[vertices][vertices];
        next = new int[vertices][vertices];

        // Initialize
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                dist[i][j] = (i == j) ? 0 : INF;
                next[i][j] = -1;
            }
        }
    }

    // Add Edge
    public void addEdge(int from, int to, int weight) {
        dist[from][to] = weight;
        next[from][to] = to;
    }

    // Floyd-Warshall Algorithm
    public void floydWarshall() {
        for (int k = 0; k < vertices; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        displayResult();
    }

    // Display All Pairs Result
    private void displayResult() {
        System.out.println("\n===== Floyd-Warshall All-Pairs Shortest Path =====");
        System.out.print("  \t");
        for (int i = 0; i < vertices; i++)
            System.out.print("Acc#" + i + "\t");
        System.out.println();
        System.out.println("  ------------------------------------------------");

        for (int i = 0; i < vertices; i++) {
            System.out.print("  Acc#" + i + "\t");
            for (int j = 0; j < vertices; j++) {
                if (dist[i][j] == INF)
                    System.out.print("INF\t");
                else
                    System.out.print("₹" + dist[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("==================================================");
    }

    // Print specific path
    public void printPath(int from, int to) {
        System.out.println("\n===== Transfer Path: Account#" + from + " → Account#" + to + " =====");
        if (dist[from][to] == INF) {
            System.out.println("  No path exists!");
            return;
        }
        System.out.print("  Path: ");
        int curr = from;
        while (curr != to) {
            System.out.print("Account#" + curr + " → ");
            curr = next[curr][to];
        }
        System.out.println("Account#" + to);
        System.out.println("  Total Cost: ₹" + dist[from][to]);
        System.out.println("=====================================================");
    }
}