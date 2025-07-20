package com.urlshortener;

import com.urlshortener.db.Database;
import com.urlshortener.db.UrlRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlRepositoryTest {

    @BeforeAll
    public void setUpDatabase() {
        // Initialize in-memory H2 DB
        Database.init();
    }

    @BeforeEach
    public void clearDatabase() throws Exception {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM urls")) {
            stmt.executeUpdate();
        }
    }

    @Test
    public void testSaveAndRetrieveUrl() {
        String longUrl = "https://www.example.com";
        String shortCode = "exmpl1";

        // Save to DB
        String returnedCode = UrlRepository.save(longUrl, shortCode);
        assertEquals(shortCode, returnedCode, "Short code should be saved and returned correctly");

        // Retrieve from DB
        String fetchedUrl = UrlRepository.findLongUrl(shortCode);
        assertEquals(longUrl, fetchedUrl, "Long URL should match the one saved");
    }

    @Test
    public void testFindUrlNotExists() {
        String result = UrlRepository.findLongUrl("nonexist");
        assertNull(result, "Should return null if short code does not exist");
    }
}
