package com.finpulse.m4_shortest_path;

import java.util.*;

public class TopologicalSort {
    private int vertices;
    private List<List<Integer>> adjList;

    public TopologicalSort(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++)
            adjList.add(new ArrayList<>());
    }

    // Add directed edge
    public void addEdge(int from, int to) {
        adjList.get(from).add(to);
    }

    // Topological Sort using DFS
    public void topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++)
            if (!visited[i])
                dfsRec(i, visited, stack);

        displayResult(stack);
    }

    private void dfsRec(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        for (int neighbor : adjList.get(node))
            if (!visited[neighbor])
                dfsRec(neighbor, visited, stack);
        stack.push(node);
    }

    // Display Result
    private void displayResult(Stack<Integer> stack) {
        System.out.println("\n===== Topological Sort - Financial Settlement Order =====");
        System.out.print("  Settlement Sequence: ");
        while (!stack.isEmpty())
            System.out.print("Operation#" + stack.pop() +
                    (stack.isEmpty() ? "" : " → "));
        System.out.println();
        System.out.println("=========================================================");
    }

    // Cycle Detection (DAG check)
    public boolean isCyclic() {
        boolean[] visited = new boolean[vertices];
        boolean[] recStack = new boolean[vertices];
        for (int i = 0; i < vertices; i++)
            if (cycleRec(i, visited, recStack)) return true;
        return false;
    }

    private boolean cycleRec(int node, boolean[] visited, boolean[] recStack) {
        if (recStack[node]) return true;
        if (visited[node]) return false;
        visited[node] = true;
        recStack[node] = true;
        for (int neighbor : adjList.get(node))
            if (cycleRec(neighbor, visited, recStack)) return true;
        recStack[node] = false;
        return false;
    }

    // Display Graph
    public void displayGraph() {
        System.out.println("\n===== Financial Operations Dependency Graph =====");
        for (int i = 0; i < vertices; i++) {
            System.out.print("  Operation#" + i + " → ");
            if (adjList.get(i).isEmpty())
                System.out.print("(no dependencies)");
            else
                for (int neighbor : adjList.get(i))
                    System.out.print("Operation#" + neighbor + " ");
            System.out.println();
        }
        System.out.println("=================================================");
    }
}