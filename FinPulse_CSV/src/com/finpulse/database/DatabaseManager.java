package com.finpulse.database;

import com.finpulse.models.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/finpulse_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "YOUR_POSTGRES_PASSWORD";

    // Connect to PostgreSQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert Account into PostgreSQL
    public static void insertAccount(Account account) {

        String sql = "INSERT INTO accounts(account_number, holder_name, balance, account_type, credit_score) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getAccountType());
            ps.setInt(5, account.getCreditScore());

            ps.executeUpdate();

            System.out.println("✅ Account saved to PostgreSQL");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}