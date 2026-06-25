package com.finpulse.m6_greedy_dp;

import java.util.*;

public class ActivitySelection {

    private static class Activity {
        int id;
        String name;
        int start;
        int end;

        Activity(int id, String name, int start, int end) {
            this.id = id;
            this.name = name;
            this.start = start;
            this.end = end;
        }
    }

    private List<Activity> activities;

    public ActivitySelection() {
        activities = new ArrayList<>();
    }

    // Add Audit Activity
    public void addActivity(int id, String name, int start, int end) {
        activities.add(new Activity(id, name, start, end));
    }

    // Greedy Activity Selection
    public void selectActivities() {
        // Sort by end time
        activities.sort(Comparator.comparingInt(a -> a.end));

        List<Activity> selected = new ArrayList<>();
        int lastEnd = -1;

        for (Activity a : activities) {
            if (a.start >= lastEnd) {
                selected.add(a);
                lastEnd = a.end;
            }
        }

        displayResult(selected);
    }

    // Display Result
    private void displayResult(List<Activity> selected) {
        System.out.println("\n===== Activity Selection - Audit Schedule =====");
        System.out.println("  Total Activities Available: " + activities.size());
        System.out.println("  Optimal Activities Selected: " + selected.size());
        System.out.println("  -----------------------------------------------");
        System.out.println("  ID  | Audit Name          | Start | End");
        System.out.println("  -----------------------------------------------");
        for (Activity a : selected) {
            System.out.printf("  %-4d| %-20s| %-6d| %d%n",
                    a.id, a.name, a.start, a.end);
        }
        System.out.println("  -----------------------------------------------");
        System.out.println("  ✓ Maximum non-overlapping audits scheduled!");
        System.out.println("===============================================");
    }

    // Display All Activities
    public void displayAll() {
        System.out.println("\n===== All Audit Activities =====");
        System.out.println("  ID  | Audit Name          | Start | End");
        System.out.println("  ------------------------------------");
        for (Activity a : activities) {
            System.out.printf("  %-4d| %-20s| %-6d| %d%n",
                    a.id, a.name, a.start, a.end);
        }
        System.out.println("================================");
    }
}