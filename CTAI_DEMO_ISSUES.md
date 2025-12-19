# CTAI Demo - Expected Security & Code Quality Issues

This project contains **intentional vulnerabilities and code quality issues** designed to demonstrate the power of Code Tracker AI (CTAI) insights.

## 🔒 Security Vulnerabilities (High Priority)

### SQL Injection
- **File**: `SecurityController.java`
- **Lines**: 25-30
- **Issue**: Direct string concatenation in SQL queries
- **Risk**: Database compromise, data theft

### Command Injection  
- **File**: `SecurityController.java`, `ConfigManager.java`
- **Lines**: 55-62, 89-95
- **Issue**: Unsanitized user input in system commands
- **Risk**: Remote code execution

### Hardcoded Credentials
- **File**: `SecurityController.java`, `ConfigManager.java`
- **Lines**: 15-17, 13-15
- **Issue**: Passwords and API keys in source code
- **Risk**: Credential exposure

### SSL/TLS Bypass
- **File**: `ApiClient.java`
- **Lines**: 18-40
- **Issue**: Disabling certificate validation
- **Risk**: Man-in-the-middle attacks

### Path Traversal
- **File**: `SecurityController.java`, `ConfigManager.java`
- **Lines**: 35-48, 35-45
- **Issue**: Unvalidated file paths
- **Risk**: Unauthorized file access

## ⚠️ Code Quality Issues

### Performance Problems
- **File**: `DataProcessor.java`
- **Lines**: 15-25
- **Issue**: O(n²) algorithm for duplicate detection
- **Impact**: Poor scalability

### Memory Leaks
- **File**: `SecurityController.java`, `DataProcessor.java`
- **Lines**: 85-90, 30-40
- **Issue**: Static collections never cleared, unclosed resources
- **Impact**: Memory exhaustion

### Thread Safety
- **File**: `DataProcessor.java`, `ConfigManager.java`
- **Lines**: 10-12, 20-25
- **Issue**: Race conditions, non-atomic operations
- **Impact**: Data corruption

## 🛡️ Best Practice Violations

### Exception Handling
- **File**: `DataProcessor.java`
- **Lines**: 50-58
- **Issue**: Empty catch blocks, swallowing exceptions
- **Impact**: Hidden failures

### Information Disclosure
- **File**: `ConfigManager.java`, `ApiClient.java`
- **Lines**: 65-72, 75-82
- **Issue**: Logging sensitive data, exposing stack traces
- **Risk**: Information leakage

### Weak Cryptography
- **File**: `SecurityController.java`, `ConfigManager.java`
- **Lines**: 50-65, 50-62
- **Issue**: MD5 hashing, DES encryption
- **Risk**: Cryptographic attacks

## 📊 Expected CTAI Rule Triggers

The code will trigger approximately **25-30 different CTAI rules** including:

- **SAST Rules**: SQL injection, command injection, XSS
- **Secrets Detection**: Hardcoded passwords, API keys
- **Code Quality**: Performance anti-patterns, memory leaks
- **Security Best Practices**: Weak crypto, insecure communication
- **Reliability**: Exception handling, null pointer risks

## 🎯 Demo Script

1. **Run CTAI scan** on the project
2. **Show high-severity security issues** (SQL injection, hardcoded secrets)
3. **Demonstrate code quality insights** (performance, memory issues)
4. **Highlight best practice violations** (logging, exception handling)
5. **Show fix suggestions** and remediation guidance

This demonstrates CTAI's comprehensive analysis across security, performance, and maintainability dimensions.