package com.urlshortener.db;

import java.sql.*;

public class UrlRepository {

    public static String save(String longUrl, String shortCode) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO urls (long_url, short_code) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, longUrl);
            ps.setString(2, shortCode);
            ps.executeUpdate();
            return shortCode;

        } catch (SQLException e) {
            System.err.println("❌ Save failed: " + e.getMessage());
            return null;
        }
    }

    public static String findLongUrl(String shortCode) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT long_url FROM urls WHERE short_code = ?")) {

            ps.setString(1, shortCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("long_url");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lookup failed: " + e.getMessage());
        }
        return null;
    }
}
