# Tech Controls System

A modern IoT device management and monitoring platform.

## Features

- 📱 Real-time device monitoring
- 🔧 Remote device configuration
- 📊 System health dashboard
- 🔒 Secure API endpoints
- ⚡ High-performance architecture

## Architecture

```
tech-controls/
├── src/main/java/
│   ├── Controller.java    - REST API endpoints
│   ├── Service.java       - Business logic
│   └── DeviceModel.java   - Data models
└── src/main/kotlin/
    └── Main.kt            - Application entry
```

## Quick Start

1. Build the project
2. Run the main application
3. Access API at `http://localhost:8080`

## API Endpoints

- `GET /device/{id}` - Get device status
- `PUT /device/{id}` - Update device settings
- `GET /health` - System health check

## Tech Stack

- Java 17
- Kotlin 1.9
- Spring Boot (planned)
- PostgreSQL (planned)

## Team

- Lead Developer: Sarah Johnson
- Product Manager: Mike Chen
- UX Designer: Lisa Rodriguez

## License

Copyright © 2024 Tech Controls Inc.
