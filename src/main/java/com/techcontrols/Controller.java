package com.techcontrols;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for Tech Controls System
 * Handles device management and monitoring endpoints
 */
public class Controller {
    
    private final com.techcontrols.Service service;

    public Controller() {
        this.service = new com.techcontrols.Service();
    }

    public Controller(com.techcontrols.Service service) {
        this.service = service;
    }

    public Map<String, Object> getDeviceStatus(String deviceId) {
        Map<String, Object> response = new HashMap<>();
        response.put("deviceId", deviceId);
        response.put("status", "online");
        response.put("temperature", 72.5);
        response.put("lastUpdate", System.currentTimeMillis());
        return response;
    }
    
    public boolean updateDevice(String deviceId, Map<String, Object> settings) {
        return service.updateDeviceSettings(deviceId, settings);
    }
    
    public Map<String, Object> getSystemHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "healthy");
        health.put("uptime", "99.9%");
        health.put("activeDevices", service.getActiveDeviceCount());
        return health;
    }
}
