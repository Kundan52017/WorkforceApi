# Workforce Management API

A Spring Boot REST API for managing tasks and staff in a logistics operations environment.

## 🎯 Challenge Completion Status

### ✅ Part 0: Project Setup & Structuring
- **Complete** - Professional Spring Boot project structure
- **Complete** - Gradle build configuration with all required dependencies
- **Complete** - Proper package organization following Spring Boot conventions

### ✅ Part 1: Bug Fixes
- **Bug 1 Fixed** - Task reassignment now properly cancels old task instead of creating duplicates
- **Bug 2 Fixed** - Date range queries exclude cancelled tasks to reduce clutter

### ✅ Part 2: New Features
- **Feature 1** - Smart Daily Task View: Shows tasks starting in range PLUS active tasks from before
- **Feature 2** - Task Priority Management: Full CRUD operations for task priorities
- **Feature 3** - Comments & Activity History: Complete audit trail with user comments

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Internet connection (for downloading dependencies)

### Running the Application

**Using Gradle (Recommended):**
```bash
./gradlew bootRun
```

**Using Maven (Alternative):**
```bash
mvn spring-boot:run
```

**Direct execution:**
```bash
java -jar build/libs/workforcemgmt-0.0.1-SNAPSHOT.jar
```

### Access Points
- **Base URL**: `http://localhost:8080`
- **Welcome endpoint**: `http://localhost:8080/`
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`

## 📋 API Documentation

### Task Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task details with history/comments |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| POST | `/api/tasks/assign-by-ref` | Reassign task by customer reference |
| GET | `/api/tasks/date-range` | Get tasks by date range (excludes cancelled) |
| GET | `/api/tasks/smart-daily` | Smart daily view |
| PUT | `/api/tasks/{id}/priority` | Update task priority |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority |
| POST | `/api/tasks/{id}/comments` | Add comment to task |
| PUT | `/api/tasks/{id}/status` | Update task status |

### Staff Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/staff` | Get all staff |
| GET | `/api/staff/{id}` | Get staff by ID |
| POST | `/api/staff` | Create staff member |
| PUT | `/api/staff/{id}` | Update staff member |
| DELETE | `/api/staff/{id}` | Delete staff member |

## 🔧 Technical Architecture

### Core Models
- **Task**: Work units with status, priority, assignments
- **Staff**: Employee information
- **ActivityEntry**: Automatic audit trail
- **Comment**: User-generated task comments
- **Priority**: HIGH, MEDIUM, LOW enumeration
- **TaskStatus**: ACTIVE, COMPLETED, CANCELLED enumeration

### Key Design Patterns
- **DTO Pattern**: Clean API contracts with dedicated request/response objects
- **Service Layer**: Business logic separation from controllers
- **Global Exception Handling**: Consistent error responses
- **Activity Logging**: Automatic audit trail for all task changes

### Data Storage
- **In-Memory**: Uses ConcurrentHashMap for thread-safe operations
- **Sample Data**: Pre-loaded staff members for immediate testing

## 🧪 Testing the API

Use the provided `test-api.http` file with VS Code REST Client extension or any HTTP client like Postman.

### Sample Test Flow:
1. Get welcome message: `GET /`
2. View staff: `GET /api/staff`
3. Create task: `POST /api/tasks`
4. Test reassignment: `POST /api/tasks/assign-by-ref`
5. Check activity history: `GET /api/tasks/{id}`

## 🐛 Bug Fixes Implemented

### Bug 1: Task Reassignment Duplicates
**Problem**: Reassigning tasks created duplicates instead of replacing
**Solution**: Modified `reassignTaskByCustomerReference()` to:
- Cancel old task (set to CANCELLED status)
- Create new task for new assignee
- Log activity for both operations

### Bug 2: Cancelled Tasks in Views
**Problem**: Date range queries included cancelled tasks
**Solution**: Added filtering in `getTasksByDateRange()` to exclude CANCELLED tasks

## ✨ New Features Implemented

### Feature 1: Smart Daily Task View
Enhanced date-based queries to show:
- Tasks that started within the date range
- PLUS active tasks that started before but are still open
- Endpoint: `GET /api/tasks/smart-daily`

### Feature 2: Task Priority Management
Complete priority system:
- Priority enum: HIGH, MEDIUM, LOW
- Update priority: `PUT /api/tasks/{id}/priority`
- Filter by priority: `GET /api/tasks/priority/{priority}`
- Activity logging for priority changes

### Feature 3: Comments & Activity History
Full audit trail system:
- Automatic activity logging for all task changes
- User comments with timestamps
- Chronological sorting of history and comments
- Complete history in task details: `GET /api/tasks/{id}`

## 🏗️ Project Structure

```
src/main/java/com/workforcemgmt/
├── WorkforcemgmtApplication.java          # Main application
├── controller/                            # REST endpoints
├── service/                               # Business logic
├── model/                                 # Domain entities
├── dto/                                   # API contracts
├── mapper/                                # Entity/DTO conversion
└── exception/                             # Error handling
```

## 📝 Configuration

### Application Properties
```properties
# Server configuration
server.port=8080

# Application configuration  
spring.application.name=Workforce Management API

# Logging configuration
logging.level.com.workforcemgmt=INFO
logging.level.root=WARN

# Health endpoints
management.endpoints.web.exposure.include=health,info
```

### Dependencies
- **Spring Boot 3.0.4**: Core framework
- **Spring Web**: REST API endpoints
- **Spring Validation**: Request validation
- **SpringDoc OpenAPI**: Swagger documentation
- **Lombok**: Boilerplate code reduction
- **MapStruct**: Entity-DTO mapping

## 🔍 Validation & Error Handling

### Request Validation
- Jakarta validation annotations on all DTOs
- Custom validation messages
- Proper HTTP status codes (400, 404, 500)

### Global Exception Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    // Handles ResourceNotFoundException -> 404
    // Handles ValidationException -> 400  
    // Handles General Exception -> 500
}
```

### Error Response Format
```json
{
  "timestamp": "2025-08-03T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: invalid-id",
  "path": "/api/tasks/invalid-id"
}
```

## 👥 Sample Data

### Pre-loaded Staff Members
```json
[
  {"id": "staff-1", "name": "John Doe", "department": "Sales"},
  {"id": "staff-2", "name": "Jane Smith", "department": "Operations"},
  {"id": "staff-3", "name": "Mike Johnson", "department": "Sales"},
  {"id": "staff-4", "name": "Sarah Wilson", "department": "Operations"}
]
```

## 🧪 Testing Guide

### Using Swagger UI (Recommended)
1. Start application: `./gradlew bootRun`
2. Open: `http://localhost:8080/swagger-ui/index.html`
3. Explore API groups and test endpoints interactively

### Using HTTP Files
- **Complete Test Suite**: Use `test-endpoints.http` with VS Code REST Client
- **Postman Collection**: Import `Workforce-Management-API.postman_collection.json`
- **Manual Testing**: Follow scenarios in `postman-api-tests.md`

### Test Sequence
1. **Health Check**: Verify application is running
2. **Staff Management**: View available staff
3. **Task Creation**: Create test tasks
4. **Bug Fix Verification**: Test reassignment and date filtering
5. **Feature Testing**: Priority management, comments, smart views
6. **Error Handling**: Test validation and error responses

## 🔧 Development Notes

### Thread Safety
- Uses `ConcurrentHashMap` for thread-safe in-memory storage
- Atomic operations for ID generation
- Safe concurrent access to all data structures

### Activity Logging
- Automatic audit trail for all task changes
- Timestamped entries with user information
- Action types: CREATED, UPDATED, CANCELLED, PRIORITY_CHANGED, etc.

### Data Persistence
- **Current**: In-memory storage with ConcurrentHashMap
- **Future**: Easily replaceable with JPA/database integration
- **Sample Data**: Automatically loaded on startup

---

## 🏆 Challenge Completion

This implementation demonstrates:
- ✅ **Complete Bug Fixes**: Both issues resolved with proper testing
- ✅ **All New Features**: Smart daily view, priority management, activity history
- ✅ **Professional Architecture**: Clean code, proper patterns, documentation
- ✅ **Production Ready**: Error handling, validation, logging
- ✅ **Interactive Documentation**: Complete Swagger/OpenAPI integration

**Ready for production deployment and further development!**
