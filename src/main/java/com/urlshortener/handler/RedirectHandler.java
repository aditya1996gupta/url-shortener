package com.urlshortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.db.UrlRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class RedirectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // /u/<shortcode>
        String shortCode = path.substring("/u/".length());

        String longUrl = UrlRepository.findLongUrl(shortCode);
        if (longUrl != null) {
            exchange.getResponseHeaders().add("Location", longUrl);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, -1); // 302
        } else {
            String response = "Short URL not found: " + shortCode;
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }
    }
}
