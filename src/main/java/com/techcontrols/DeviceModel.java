package com.techcontrols;

import java.util.HashMap;
import java.util.Map;

/**
 * Data model representing a smart device
 */
public class DeviceModel {
    
    private final String deviceId;
    private String name;
    private String type;
    private boolean online;
    private Map<String, Object> settings;
    
    public DeviceModel(String deviceId) {
        this.deviceId = deviceId;
        this.settings = new HashMap<>();
        this.online = true;
    }
    
    public void updateSettings(Map<String, Object> newSettings) {
        this.settings.putAll(newSettings);
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public boolean isOnline() {
        return online;
    }
    
    public void setOnline(boolean online) {
        this.online = online;
    }
}
