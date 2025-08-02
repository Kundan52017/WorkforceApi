# Workforce Management API

## Overview

This is a Spring Boot-based Workforce Management API designed for a logistics super-app. The system manages tasks, staff assignments, and workflows for employee productivity tracking. The application handles core concepts including Tasks (units of work), Staff (employees), Status tracking (ACTIVE, COMPLETED, CANCELLED), and Priority management. The system is built to help managers create, assign, and track tasks for their employees efficiently.

**Current Status: FULLY OPERATIONAL** - All features implemented and tested successfully on August 2, 2025. The API is running on port 5000 with comprehensive task management, staff management, activity tracking, and comment systems working perfectly.

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Backend Framework
- **Spring Boot with Gradle**: Chosen for rapid development, extensive ecosystem, and enterprise-grade features
- **RESTful API Design**: Standard HTTP endpoints for task and staff management operations
- **Layered Architecture**: Clear separation between controllers, services, models, and DTOs for maintainability

### Project Structure
```
src/main/java/com/company/workforcemgmt/
├── WorkforcemgmtApplication.java
├── controller/ (API endpoints)
├── service/ (Business logic)
├── model/ (Domain entities)
└── dto/ (Data transfer objects)
```

### Core Components
- **TaskController**: Handles HTTP requests for task operations including creation, assignment, and status updates
- **TaskService**: Contains business logic for task management, assignment workflows, and validation
- **Task Model**: Core entity representing work units with properties like title, status, dates, and priority
- **Staff Model**: Represents employees who can be assigned to tasks

### Data Management
- **In-Memory Storage**: Currently using collections for data persistence (suitable for development/demo)
- **DTO Pattern**: Separation between internal models and API responses for better data control
- **Task Assignment Logic**: Supports task reassignment with duplicate prevention mechanisms

### Key Features
- **Task CRUD Operations**: Create, read, update, and delete tasks
- **Staff Assignment**: Assign and reassign tasks to employees
- **Status Tracking**: Monitor task progress through ACTIVE, COMPLETED, CANCELLED states
- **Priority System**: Categorize tasks by importance levels
- **Assignment by Reference**: Reassign tasks using reference IDs to prevent duplication

## External Dependencies

### Core Spring Dependencies
- **Spring Web**: Provides REST API capabilities and HTTP request handling
- **Spring Boot Starter**: Foundation framework for auto-configuration and embedded server

### Development Tools
- **Manual Implementation**: Removed Lombok and MapStruct dependencies for better compatibility. All DTOs and mappers now use standard Java code with manual getters, setters, and mapping methods.

### Build System
- **Gradle**: Build automation and dependency management system chosen for its flexibility and performance

### Potential Future Dependencies
- **Database Integration**: Ready for JPA/Hibernate integration when persistent storage is needed
- **Validation Framework**: Spring Boot Validation for request validation
- **Testing Framework**: JUnit and Spring Test for comprehensive testing coverage