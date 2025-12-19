package com.techcontrols;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import javax.servlet.http.HttpServletRequest;

/**
 * Security Controller with intentional vulnerabilities for CTAI demo
 */
public class SecurityController {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/techcontrols";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "password123"; // Hardcoded password
    
    private Connection connection;
    
    public SecurityController() throws SQLException {
        // SQL Injection vulnerability
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    // SQL Injection vulnerability
    public User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = '" + username + 
                      "' AND password = '" + password + "'";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if (rs.next()) {
            return new User(rs.getString("username"), rs.getString("email"));
        }
        return null;
    }
    
    // Path traversal vulnerability
    public String readFile(String filename) throws IOException {
        File file = new File("/app/data/" + filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close(); // Resource leak potential
        return content.toString();
    }
    
    // Weak cryptography
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace(); // Information disclosure
            return null;
        }
    }
    
    // Command injection vulnerability
    public String executeCommand(String userInput) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ping " + userInput);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }
    
    // XSS vulnerability
    public String generateUserProfile(HttpServletRequest request) {
        String name = request.getParameter("name");
        String bio = request.getParameter("bio");
        
        return "<html><body>" +
               "<h1>Welcome " + name + "</h1>" +
               "<p>Bio: " + bio + "</p>" +
               "</body></html>";
    }
    
    // Insecure random number generation
    public String generateSessionToken() {
        Random random = new Random();
        return String.valueOf(random.nextLong());
    }
    
    // Null pointer dereference
    public void processUser(User user) {
        System.out.println("Processing user: " + user.getUsername().toUpperCase());
        // No null check
    }
    
    // Memory leak - static collection
    private static Map<String, String> sessionCache = new HashMap<>();
    
    public void cacheSession(String sessionId, String userData) {
        sessionCache.put(sessionId, userData); // Never cleaned up
    }
    
    // Infinite loop potential
    public void processQueue(List<String> queue) {
        int index = 0;
        while (index < queue.size()) {
            String item = queue.get(index);
            if (item.equals("retry")) {
                queue.add("retry"); // Can cause infinite growth
            }
            index++;
        }
    }
}

class User {
    private String username;
    private String email;
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}