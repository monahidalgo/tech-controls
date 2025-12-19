package com.techcontrols;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Configuration manager with security and reliability issues
 */
public class ConfigManager {
    
    // Sensitive data exposure
    public static final String DATABASE_PASSWORD = "MySecretPassword123!";
    public static final String ENCRYPTION_KEY = "1234567890123456";
    public static final String JWT_SECRET = "jwt_secret_key_not_secure";
    
    private Properties config;
    private static ConfigManager instance;
    
    // Singleton with thread safety issues
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager(); // Race condition
        }
        return instance;
    }
    
    private ConfigManager() {
        config = new Properties();
        loadConfig();
    }
    
    // Path traversal vulnerability
    public void loadConfig() {
        try {
            String configPath = System.getProperty("config.path", "../config/app.properties");
            FileInputStream fis = new FileInputStream(configPath);
            config.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            // Continuing with empty config - potential issues
        }
    }
    
    // Insecure file permissions
    public void saveConfig() throws IOException {
        String configPath = "/tmp/app_config.properties";
        FileOutputStream fos = new FileOutputStream(configPath);
        config.store(fos, "Application Configuration");
        fos.close();
        
        // Setting world-readable permissions
        File configFile = new File(configPath);
        configFile.setReadable(true, false);
        configFile.setWritable(true, false);
    }
    
    // Weak encryption
    public String encryptSensitiveData(String data) {
        try {
            Cipher cipher = Cipher.getInstance("DES"); // Weak algorithm
            SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "DES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return data; // Returning plaintext on error
        }
    }
    
    // Information disclosure
    public void logConfiguration() {
        System.out.println("=== Configuration Debug ===");
        for (String key : config.stringPropertyNames()) {
            String value = config.getProperty(key);
            System.out.println(key + " = " + value); // Logging sensitive data
        }
        System.out.println("Database Password: " + DATABASE_PASSWORD);
        System.out.println("Encryption Key: " + ENCRYPTION_KEY);
    }
    
    // Command injection via configuration
    public void executeConfigCommand(String command) {
        try {
            String fullCommand = "sh -c '" + command + "'";
            Runtime.getRuntime().exec(fullCommand); // Command injection
        } catch (IOException e) {
            System.err.println("Command execution failed: " + e.getMessage());
        }
    }
    
    // Unsafe reflection
    public Object createInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance(); // Deprecated and unsafe
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance", e);
        }
    }
    
    // XML External Entity (XXE) vulnerability
    public void parseXmlConfig(String xmlContent) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = 
                javax.xml.parsers.DocumentBuilderFactory.newInstance();
            // Not disabling external entities - XXE vulnerability
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            java.io.StringReader reader = new java.io.StringReader(xmlContent);
            org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
            builder.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Insecure temporary file creation
    public File createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("config", ".tmp");
        // File created with default permissions - security risk
        
        FileWriter writer = new FileWriter(tempFile);
        writer.write(content);
        writer.close();
        
        return tempFile;
    }
    
    // Buffer overflow potential
    public void processConfigData(byte[] data) {
        byte[] buffer = new byte[1024];
        System.arraycopy(data, 0, buffer, 0, data.length); // No bounds check
    }
    
    // Denial of Service via regex
    public boolean validateConfigValue(String value) {
        // Catastrophic backtracking regex
        String pattern = "(a+)+b";
        return value.matches(pattern);
    }
}