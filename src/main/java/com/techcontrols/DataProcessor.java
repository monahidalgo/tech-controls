package com.techcontrols;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

/**
 * Data processor with performance and security issues
 */
public class DataProcessor {
    
    // Thread safety issues
    private static int counter = 0;
    private List<String> sharedList = new ArrayList<>();
    
    // Inefficient algorithms
    public List<String> findDuplicates(List<String> data) {
        List<String> duplicates = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i).equals(data.get(j))) {
                    duplicates.add(data.get(i));
                }
            }
        }
        return duplicates; // O(n²) complexity
    }
    
    // Resource leaks
    public String downloadData(String url) throws Exception {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setConnectTimeout(30000); // Long timeout
        
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        // Missing close() calls - resource leak
        return result.toString();
    }
    
    // Synchronization issues
    public void incrementCounter() {
        counter++; // Race condition
        sharedList.add("item" + counter); // Not thread-safe
    }
    
    // Memory inefficiency
    public String processLargeData(List<String> data) {
        String result = "";
        for (String item : data) {
            result += item + ","; // String concatenation in loop
        }
        return result;
    }
    
    // Exception handling issues
    public void processFile(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            fis.read(buffer);
            fis.close();
        } catch (Exception e) {
            // Empty catch block - swallowing exceptions
        }
    }
    
    // Potential DoS vulnerability
    public void processUserData(Map<String, Object> userData) {
        List<String> items = new ArrayList<>();
        Integer count = (Integer) userData.get("count");
        
        // No bounds checking
        for (int i = 0; i < count; i++) {
            items.add("item" + i);
        }
        
        // Recursive call without termination check
        if (count > 0) {
            Map<String, Object> newData = new HashMap<>(userData);
            newData.put("count", count - 1);
            processUserData(newData);
        }
    }
    
    // Hardcoded secrets
    private static final String API_KEY = "sk-1234567890abcdef";
    private static final String SECRET_TOKEN = "secret_token_12345";
    
    public boolean validateApiKey(String providedKey) {
        return API_KEY.equals(providedKey); // Timing attack vulnerability
    }
    
    // Insecure deserialization
    public Object deserializeData(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject(); // Unsafe deserialization
    }
    
    // Integer overflow
    public int calculateTotal(int[] numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num; // Potential overflow
        }
        return total;
    }
    
    // Weak random generation for security
    public String generatePassword() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder password = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString(); // Weak password generation
    }
}