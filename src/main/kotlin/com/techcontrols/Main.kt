package com.techcontrols

import com.techcontrols.Service
import com.techcontrols.Controller

/**
 * Main application entry point
 * Initializes the Tech Controls system
 */
fun main() {
    println("🚀 Tech Controls System Starting...")
    
    val service = Service()
    val controller = Controller(service)
    
    // Initialize system
    val health = controller.systemHealth
    println("✅ System Status: ${health["status"]}")
    println("📊 Active Devices: ${health["activeDevices"]}")
    println("⏱️  Uptime: ${health["uptime"]}")
    
    // Test device
    val deviceStatus = controller.getDeviceStatus("device-001")
    println("\n📱 Device Status:")
    println("   ID: ${deviceStatus["deviceId"]}")
    println("   Status: ${deviceStatus["status"]}")
    println("   Temperature: ${deviceStatus["temperature"]}°F")
    
    println("\n✨ System Ready!")
}

/**
 * Configuration manager for system settings
 */
object Config {
    const val API_VERSION = "v1.0"
    const val MAX_DEVICES = 100
    const val TIMEOUT_MS = 5000L
}
