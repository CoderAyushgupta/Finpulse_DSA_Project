package com.finpulse.m6_greedy_dp;

public class LCSAndEditDistance {

    // ==================== LCS ====================

    // Longest Common Subsequence
    public int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        displayLCS(s1, s2, dp);
        return dp[m][n];
    }

    // Get LCS String
    private String getLCSString(String s1, String s2, int[][] dp) {
        int i = s1.length(), j = s2.length();
        StringBuilder lcs = new StringBuilder();
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.insert(0, s1.charAt(i - 1));
                i--; j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return lcs.toString();
    }

    // Display LCS Result
    private void displayLCS(String s1, String s2, int[][] dp) {
        String lcsStr = getLCSString(s1, s2, dp);
        System.out.println("\n===== LCS - Financial Document Matching =====");
        System.out.println("  Document 1 : " + s1);
        System.out.println("  Document 2 : " + s2);
        System.out.println("  LCS Length : " + dp[s1.length()][s2.length()]);
        System.out.println("  LCS String : " + lcsStr);
        double similarity = (double) dp[s1.length()][s2.length()] /
                Math.max(s1.length(), s2.length()) * 100;
        System.out.printf("  Similarity : %.2f%%%n", similarity);
        if (similarity >= 70)
            System.out.println("  ✓ Documents are SIMILAR - Possible duplicate!");
        else
            System.out.println("  ✗ Documents are DIFFERENT");
        System.out.println("=============================================");
    }

    // ==================== EDIT DISTANCE ====================

    // Edit Distance (Levenshtein)
    public int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }

        displayEditDistance(s1, s2, dp[m][n]);
        return dp[m][n];
    }

    // Display Edit Distance Result
    private void displayEditDistance(String s1, String s2, int distance) {
        System.out.println("\n===== Edit Distance - Document Verification =====");
        System.out.println("  Document 1    : " + s1);
        System.out.println("  Document 2    : " + s2);
        System.out.println("  Edit Distance : " + distance);
        if (distance == 0)
            System.out.println("  ✓ Documents are IDENTICAL");
        else if (distance <= 3)
            System.out.println("  ⚠️  Documents are VERY SIMILAR - Possible forgery!");
        else if (distance <= 7)
            System.out.println("  ~ Documents are SOMEWHAT SIMILAR");
        else
            System.out.println("  ✗ Documents are DIFFERENT");
        System.out.println("=================================================");
    }
}