package com.finpulse.m3_graphs;

import java.util.*;

public class TransactionGraph {
    private int vertices;
    private Map<Integer, List<int[]>> adjList; // accountNo → [neighborNo, weight]

    public TransactionGraph(int vertices) {
        this.vertices = vertices;
        adjList = new HashMap<>();
        for (int i = 0; i < vertices; i++)
            adjList.put(i, new ArrayList<>());
    }

    // Add directed edge (transaction)
    public void addTransaction(int from, int to, int amount) {
        adjList.get(from).add(new int[]{to, amount});
    }

    // Add undirected edge
    public void addUndirectedTransaction(int from, int to, int amount) {
        adjList.get(from).add(new int[]{to, amount});
        adjList.get(to).add(new int[]{from, amount});
    }

    // BFS - Transaction Connectivity
    public void bfs(int start) {
        System.out.println("\n===== BFS Transaction Network (from Account " + start + ") =====");
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print("  Account#" + node + " → ");
            for (int[] neighbor : adjList.get(node)) {
                if (!visited[neighbor[0]]) {
                    visited[neighbor[0]] = true;
                    queue.add(neighbor[0]);
                }
            }
            System.out.println();
        }
        System.out.println("=============================================================");
    }

    // DFS - Detect Suspicious Loops
    public void dfs(int start) {
        System.out.println("\n===== DFS Fraud Loop Detection (from Account " + start + ") =====");
        boolean[] visited = new boolean[vertices];
        dfsRec(start, visited);
        System.out.println("\n==============================================================");
    }

    private void dfsRec(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print("  Account#" + node + " ");
        for (int[] neighbor : adjList.get(node)) {
            if (!visited[neighbor[0]])
                dfsRec(neighbor[0], visited);
        }
    }

    // Cycle Detection - Fraud Loop
    public boolean detectCycle() {
        boolean[] visited = new boolean[vertices];
        boolean[] recStack = new boolean[vertices];
        for (int i = 0; i < vertices; i++)
            if (detectCycleRec(i, visited, recStack)) return true;
        return false;
    }

    private boolean detectCycleRec(int node, boolean[] visited, boolean[] recStack) {
        if (recStack[node]) return true;
        if (visited[node]) return false;
        visited[node] = true;
        recStack[node] = true;
        for (int[] neighbor : adjList.get(node))
            if (detectCycleRec(neighbor[0], visited, recStack)) return true;
        recStack[node] = false;
        return false;
    }

    // Display Graph
    public void displayGraph() {
        System.out.println("\n===== Transaction Network Graph =====");
        for (int i = 0; i < vertices; i++) {
            System.out.print("  Account#" + i + " → ");
            for (int[] neighbor : adjList.get(i))
                System.out.print("Account#" + neighbor[0] + "(₹" + neighbor[1] + ") ");
            System.out.println();
        }
        System.out.println("=====================================");
    }

    // Fraud Alert
    public void fraudAlert() {
        System.out.println("\n===== Fraud Detection Report =====");
        boolean cycle = detectCycle();
        if (cycle)
            System.out.println("  ⚠️  SUSPICIOUS LOOP DETECTED! Possible Money Laundering!");
        else
            System.out.println("  ✓  No suspicious loops detected.");
        System.out.println("==================================");
    }

    public int getVertices() { return vertices; }
    public Map<Integer, List<int[]>> getAdjList() { return adjList; }
}