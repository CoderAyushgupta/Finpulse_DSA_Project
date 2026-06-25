package com.finpulse.database;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        try {

            Connection connection = DatabaseManager.getConnection();

            System.out.println("=================================");
            System.out.println(" PostgreSQL Connected Successfully ");
            System.out.println("=================================");

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}