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

1. **Navigate to project directory:**
   ```bash
   cd WorkforceAPI
   ```

2. **Start the application:**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the API:**
   - Base URL: `http://localhost:5000`
   - Welcome endpoint: `http://localhost:5000/`
   - Health check: `http://localhost:5000/health`

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

Key configurations in `application.properties`:
- Server port: 5000
- Logging levels configured
- Health endpoints enabled

## 🔍 Validation & Error Handling

- Jakarta validation on all request DTOs
- Global exception handler for consistent error responses
- Proper HTTP status codes
- Detailed error messages for validation failures

## 👥 Sample Data

Pre-loaded staff members:
- staff-1: John Doe (Sales)
- staff-2: Jane Smith (Operations)
- staff-3: Mike Johnson (Sales)
- staff-4: Sarah Wilson (Operations)
