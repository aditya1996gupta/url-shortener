package com.urlshortener.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // H2 database path is relative to project root
    //jdbc:h2:file:C:/Users/Aditya/Desktop/url-shortener/data/urlshortener

    private static final String DB_URL = "jdbc:h2:file:C:/Users/Aditya/Desktop/url-shortener/data/urlshortener"; // creates ./data/urlshortener.mv.db
    private static final String DB_USER = "sa"; // default user for H2
    private static final String DB_PASS = "";   // default password is empty

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            String createTable = "CREATE TABLE IF NOT EXISTS urls (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "long_url VARCHAR(2048) NOT NULL, " +
                    "short_code VARCHAR(255) NOT NULL UNIQUE" +
                    ")";
            stmt.execute(createTable);
            System.out.println("✅ Database initialized.");
        } catch (SQLException e) {
            System.err.println("❌ Database init failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
