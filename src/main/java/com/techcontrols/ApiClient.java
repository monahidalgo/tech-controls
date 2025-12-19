package com.techcontrols;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

/**
 * API Client with SSL/TLS and network security issues
 */
public class ApiClient {
    
    private static final Logger logger = Logger.getLogger(ApiClient.class.getName());
    private static final String API_BASE_URL = "http://api.techcontrols.com"; // HTTP instead of HTTPS
    
    // SSL/TLS security bypass
    public void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true; // Accept all hostnames
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Insecure HTTP communication
    public String makeApiCall(String endpoint, String data) throws IOException {
        String url = API_BASE_URL + endpoint;
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        
        // No timeout settings - potential DoS
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        
        // Sending sensitive data over HTTP
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return response.toString();
    }
    
    // Server-Side Request Forgery (SSRF)
    public String fetchUrl(String userProvidedUrl) throws IOException {
        URL url = new URL(userProvidedUrl); // No validation
        URLConnection connection = url.openConnection();
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        
        return content.toString();
    }
    
    // Credential exposure in logs
    public void authenticateUser(String username, String password) {
        logger.info("Authenticating user: " + username + " with password: " + password);
        
        try {
            String authData = "username=" + username + "&password=" + password;
            String response = makeApiCall("/auth", authData);
            logger.info("Authentication response: " + response);
        } catch (IOException e) {
            logger.severe("Authentication failed: " + e.getMessage());
        }
    }
    
    // Insecure cookie handling
    public void handleCookies(HttpURLConnection connection) {
        String cookies = connection.getHeaderField("Set-Cookie");
        if (cookies != null) {
            // Not checking for secure flags
            System.setProperty("http.cookie", cookies);
            logger.info("Stored cookies: " + cookies); // Logging sensitive data
        }
    }
    
    // Open redirect vulnerability
    public void redirectUser(String redirectUrl) throws IOException {
        // No validation of redirect URL
        URL url = new URL(redirectUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.connect();
    }
    
    // Improper error handling exposing system info
    public String processApiResponse(String response) {
        try {
            if (response.contains("error")) {
                throw new RuntimeException("API Error: " + response);
            }
            return response.toUpperCase();
        } catch (Exception e) {
            // Exposing stack trace to user
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "Error occurred: " + sw.toString();
        }
    }
    
    // Unvalidated file upload
    public void uploadFile(String filename, byte[] fileData) throws IOException {
        String uploadUrl = API_BASE_URL + "/upload?filename=" + filename;
        URL url = new URL(uploadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        
        OutputStream os = connection.getOutputStream();
        os.write(fileData); // No size limits or content validation
        os.close();
        
        int responseCode = connection.getResponseCode();
        logger.info("Upload response code: " + responseCode);
    }
}