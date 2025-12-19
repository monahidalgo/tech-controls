package com.techcontrols;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Business logic service for device management
 */
public class Service {
    
    private final Map<String, DeviceModel> devices = new ConcurrentHashMap<>();
    
    public boolean updateDeviceSettings(String deviceId, Map<String, Object> settings) {
        DeviceModel device = devices.get(deviceId);
        if (device == null) {
            device = new DeviceModel(deviceId);
            devices.put(deviceId, device);
        }
        device.updateSettings(settings);
        return true;
    }
    
    public DeviceModel getDevice(String deviceId) {
        return devices.get(deviceId);
    }
    
    public int getActiveDeviceCount() {
        return devices.size();
    }
}
