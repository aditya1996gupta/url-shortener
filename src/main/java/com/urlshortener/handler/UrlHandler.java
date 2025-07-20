package com.urlshortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.db.UrlRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UrlHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        // CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equalsIgnoreCase(method)) {
            // Handle preflight request
            exchange.sendResponseHeaders(204, -1); // No Content
            return;
        }

        if ("POST".equalsIgnoreCase(method)) {
            InputStream is = exchange.getRequestBody();
            String longUrl = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String shortCode = UUID.randomUUID().toString().substring(0, 6);
            UrlRepository.save(longUrl, shortCode);

            String shortUrl = "http://localhost:8081/u/" + shortCode;
            String response = "{\"shortUrl\": \"" + shortUrl + "\"}";

            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
