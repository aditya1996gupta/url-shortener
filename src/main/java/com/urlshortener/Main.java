package com.urlshortener;

import com.sun.net.httpserver.HttpServer;
import com.urlshortener.db.Database;
import com.urlshortener.handler.UrlHandler;
import com.urlshortener.handler.RedirectHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the database
            Database.init();

            // Create and start HTTP server on port 8081
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

            // Register endpoints
            server.createContext("/shorten", new UrlHandler());     // POST
            server.createContext("/u", new RedirectHandler());      // GET

            server.setExecutor(null); // Use default thread pool
            System.out.println("🚀 Server running at http://localhost:8081");
            server.start();
        } catch (IOException e) {
            System.err.println("❌ Server failed to start: " + e.getMessage());
        }
    }
}
