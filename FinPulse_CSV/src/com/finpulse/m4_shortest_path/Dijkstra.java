package com.finpulse.m4_shortest_path;

import java.util.*;

public class Dijkstra {
    private int vertices;
    private int[][] graph;

    public Dijkstra(int vertices) {
        this.vertices = vertices;
        graph = new int[vertices][vertices];
    }

    // Add Edge
    public void addEdge(int from, int to, int weight) {
        graph[from][to] = weight;
        graph[to][from] = weight;
    }

    // Find minimum distance vertex
    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < vertices; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Dijkstra Algorithm
    public void dijkstra(int src) {
        int[] dist = new int[vertices];
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        for (int count = 0; count < vertices - 1; count++) {
            int u = minDistance(dist, visited);
            if (u == -1) break;
            visited[u] = true;

            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && graph[u][v] != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        displayResult(src, dist, parent);
    }

    // Display Result
    private void displayResult(int src, int[] dist, int[] parent) {
        System.out.println("\n===== Dijkstra Shortest Path (from Account#" + src + ") =====");
        System.out.println("  Account\t| Min Cost\t| Path");
        System.out.println("  ------------------------------------------------");
        for (int i = 0; i < vertices; i++) {
            if (i != src) {
                System.out.print("  Account#" + i + "\t| ₹" + dist[i] + "\t\t| ");
                printPath(parent, i);
                System.out.println();
            }
        }
        System.out.println("==========================================================");
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