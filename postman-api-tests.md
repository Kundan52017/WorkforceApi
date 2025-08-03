# Workforce Management API - Complete Postman Collection
# Copy these requests into Postman or use as curl commands

## BASE URL
http://localhost:8080

## ========================================
## 1. WELCOME & HEALTH ENDPOINTS
## ========================================

### 1.1 Welcome Message
GET http://localhost:8080/
Accept: application/json

### 1.2 Basic Health Check (Simple endpoint)
GET http://localhost:8080/health
Accept: application/json

### 1.3 Actuator Health Check (Detailed monitoring)
# Note: Requires spring-boot-starter-actuator dependency
GET http://localhost:8080/actuator/health
Accept: application/json

### 1.4 Application Info (Actuator)
GET http://localhost:8080/actuator/info
Accept: application/json

### 1.5 Metrics Overview (Actuator)
GET http://localhost:8080/actuator/metrics
Accept: application/json

## ========================================
## 2. STAFF MANAGEMENT ENDPOINTS
## ========================================

### 2.1 Get All Staff (Pre-loaded Data)
GET http://localhost:8080/api/staff
Accept: application/json

### 2.2 Get Staff by ID
GET http://localhost:8080/api/staff/staff-1
Accept: application/json

### 2.3 Create New Staff Member
POST http://localhost:8080/api/staff
Content-Type: application/json

{
  "name": "Alice Brown",
  "email": "alice.brown@company.com",
  "department": "Marketing"
}

### 2.4 Update Existing Staff
PUT http://localhost:8080/api/staff/staff-1
Content-Type: application/json

{
  "id": "staff-1",
  "name": "John Doe Updated",
  "email": "john.doe.updated@company.com",
  "department": "Senior Sales"
}

### 2.5 Delete Staff Member
DELETE http://localhost:8080/api/staff/staff-5
Accept: application/json

## ========================================
## 3. TASK MANAGEMENT - BASIC CRUD
## ========================================

### 3.1 Get All Tasks
GET http://localhost:8080/api/tasks
Accept: application/json

### 3.2 Create New Task
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Process Customer Order #12345",
  "description": "Handle customer order and coordinate delivery for premium client",
  "priority": "HIGH",
  "assignedStaffId": "staff-1",
  "startDate": "2025-08-02",
  "dueDate": "2025-08-05",
  "createdBy": "manager-1",
  "customerReference": "CUST-12345"
}

### 3.3 Create Another Task for Testing
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Inventory Management Review",
  "description": "Monthly inventory check and stock reconciliation",
  "priority": "MEDIUM",
  "assignedStaffId": "staff-2",
  "startDate": "2025-08-01",
  "dueDate": "2025-08-03",
  "createdBy": "manager-2",
  "customerReference": "CUST-67890"
}

### 3.4 Create Third Task for Priority Testing
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Urgent Customer Support Issue",
  "description": "Critical customer complaint requiring immediate attention",
  "priority": "HIGH",
  "assignedStaffId": "staff-3",
  "startDate": "2025-07-30",
  "dueDate": "2025-08-02",
  "createdBy": "support-manager",
  "customerReference": "CUST-URGENT"
}

### 3.5 Get Task Details (Replace {taskId} with actual ID from create response)
GET http://localhost:8080/api/tasks/{taskId}
Accept: application/json

### 3.6 Update Task
PUT http://localhost:8080/api/tasks/{taskId}
Content-Type: application/json

{
  "id": "{taskId}",
  "title": "Updated Task Title",
  "description": "Updated description with more details",
  "status": "ACTIVE",
  "priority": "MEDIUM",
  "assignedStaffId": "staff-2",
  "assignedStaffName": "Jane Smith",
  "startDate": "2025-08-02",
  "dueDate": "2025-08-06",
  "createdBy": "manager-1",
  "customerReference": "CUST-12345"
}

### 3.7 Delete Task
DELETE http://localhost:8080/api/tasks/{taskId}
Accept: application/json

## ========================================
## 4. BUG FIX DEMONSTRATIONS
## ========================================

### 4.1 BUG FIX 1: Task Reassignment (No Duplicates)
# First create a task, then reassign it
POST http://localhost:8080/api/tasks/assign-by-ref?customerReference=CUST-12345&newStaffId=staff-2&updatedBy=manager-1
Accept: application/json

### 4.2 BUG FIX 1: Alternative Reassignment Test
POST http://localhost:8080/api/tasks/assign-by-ref?customerReference=CUST-67890&newStaffId=staff-3&updatedBy=manager-2
Accept: application/json

### 4.3 BUG FIX 2: Date Range Query (Excludes Cancelled Tasks)
GET http://localhost:8080/api/tasks/date-range?startDate=2025-08-01&endDate=2025-08-10
Accept: application/json

### 4.4 BUG FIX 2: Different Date Range Test
GET http://localhost:8080/api/tasks/date-range?startDate=2025-07-25&endDate=2025-08-05
Accept: application/json

## ========================================
## 5. NEW FEATURE 1: SMART DAILY TASK VIEW
## ========================================

### 5.1 Smart Daily Tasks - Today's Work
GET http://localhost:8080/api/tasks/smart-daily?startDate=2025-08-02&endDate=2025-08-02
Accept: application/json

### 5.2 Smart Daily Tasks - This Week
GET http://localhost:8080/api/tasks/smart-daily?startDate=2025-08-01&endDate=2025-08-03
Accept: application/json

### 5.3 Smart Daily Tasks - Extended Range
GET http://localhost:8080/api/tasks/smart-daily?startDate=2025-07-30&endDate=2025-08-05
Accept: application/json

## ========================================
## 6. NEW FEATURE 2: PRIORITY MANAGEMENT
## ========================================

### 6.1 Update Task Priority to HIGH
PUT http://localhost:8080/api/tasks/{taskId}/priority
Content-Type: application/json

{
  "priority": "HIGH",
  "updatedBy": "manager-1"
}

### 6.2 Update Task Priority to LOW
PUT http://localhost:8080/api/tasks/{taskId}/priority
Content-Type: application/json

{
  "priority": "LOW",
  "updatedBy": "manager-2"
}

### 6.3 Update Task Priority to MEDIUM
PUT http://localhost:8080/api/tasks/{taskId}/priority
Content-Type: application/json

{
  "priority": "MEDIUM",
  "updatedBy": "supervisor-1"
}

### 6.4 Get All HIGH Priority Tasks
GET http://localhost:8080/api/tasks/priority/HIGH
Accept: application/json

### 6.5 Get All MEDIUM Priority Tasks
GET http://localhost:8080/api/tasks/priority/MEDIUM
Accept: application/json

### 6.6 Get All LOW Priority Tasks
GET http://localhost:8080/api/tasks/priority/LOW
Accept: application/json

## ========================================
## 7. NEW FEATURE 3: COMMENTS & ACTIVITY HISTORY
## ========================================

### 7.1 Add Comment to Task
POST http://localhost:8080/api/tasks/{taskId}/comments
Content-Type: application/json

{
  "content": "Customer called to confirm delivery address. Updated to 123 Main Street.",
  "userId": "staff-1",
  "userName": "John Doe"
}

### 7.2 Add Another Comment
POST http://localhost:8080/api/tasks/{taskId}/comments
Content-Type: application/json

{
  "content": "Coordinated with logistics team. Delivery scheduled for tomorrow.",
  "userId": "staff-2",
  "userName": "Jane Smith"
}

### 7.3 Add Manager Comment
POST http://localhost:8080/api/tasks/{taskId}/comments
Content-Type: application/json

{
  "content": "Excellent work on this task. Customer is very satisfied.",
  "userId": "manager-1",
  "userName": "Manager Johnson"
}

### 7.4 Add Status Update Comment
POST http://localhost:8080/api/tasks/{taskId}/comments
Content-Type: application/json

{
  "content": "Task completed successfully. All deliverables met quality standards.",
  "userId": "staff-1",
  "userName": "John Doe"
}

### 7.5 Get Task with Full Activity History and Comments
GET http://localhost:8080/api/tasks/{taskId}
Accept: application/json

## ========================================
## 8. TASK STATUS MANAGEMENT
## ========================================

### 8.1 Update Task Status to COMPLETED
PUT http://localhost:8080/api/tasks/{taskId}/status?status=COMPLETED&updatedBy=staff-1
Accept: application/json

### 8.2 Update Task Status to CANCELLED
PUT http://localhost:8080/api/tasks/{taskId}/status?status=CANCELLED&updatedBy=manager-1
Accept: application/json

### 8.3 Update Task Status back to ACTIVE
PUT http://localhost:8080/api/tasks/{taskId}/status?status=ACTIVE&updatedBy=manager-1
Accept: application/json

## ========================================
## 9. COMPREHENSIVE TESTING WORKFLOW
## ========================================

### 9.1 Complete Feature Demo Sequence:

# Step 1: Check welcome and staff
GET http://localhost:8080/
GET http://localhost:8080/api/staff

# Step 2: Create test tasks
POST http://localhost:8080/api/tasks
{
  "title": "Demo Task 1",
  "description": "First demo task for testing",
  "priority": "HIGH",
  "assignedStaffId": "staff-1",
  "startDate": "2025-08-02",
  "dueDate": "2025-08-05",
  "createdBy": "demo-manager",
  "customerReference": "DEMO-001"
}

# Step 3: Test bug fix 1 (reassignment)
POST http://localhost:8080/api/tasks/assign-by-ref?customerReference=DEMO-001&newStaffId=staff-2&updatedBy=demo-manager

# Step 4: Test bug fix 2 (date range)
GET http://localhost:8080/api/tasks/date-range?startDate=2025-08-01&endDate=2025-08-10

# Step 5: Test feature 1 (smart daily view)
GET http://localhost:8080/api/tasks/smart-daily?startDate=2025-08-02&endDate=2025-08-02

# Step 6: Test feature 2 (priority management)
PUT http://localhost:8080/api/tasks/{taskId}/priority
{
  "priority": "LOW",
  "updatedBy": "demo-manager"
}

GET http://localhost:8080/api/tasks/priority/LOW

# Step 7: Test feature 3 (comments & history)
POST http://localhost:8080/api/tasks/{taskId}/comments
{
  "content": "Demo comment showing activity tracking",
  "userId": "demo-user",
  "userName": "Demo User"
}

GET http://localhost:8080/api/tasks/{taskId}

## ========================================
## 10. ERROR TESTING (Optional)
## ========================================

### 10.1 Test Invalid Staff ID
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Test Invalid Staff",
  "description": "This should fail",
  "priority": "HIGH",
  "assignedStaffId": "invalid-staff-id",
  "startDate": "2025-08-02",
  "dueDate": "2025-08-05",
  "createdBy": "manager-1",
  "customerReference": "INVALID-TEST"
}

### 10.2 Test Invalid Task ID
GET http://localhost:8080/api/tasks/invalid-task-id
Accept: application/json

### 10.3 Test Invalid Priority
PUT http://localhost:8080/api/tasks/{taskId}/priority
Content-Type: application/json

{
  "priority": "INVALID_PRIORITY",
  "updatedBy": "manager-1"
}

### 10.4 Test Missing Required Fields
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "",
  "description": "Missing required fields test"
}

## ========================================
## NOTES FOR POSTMAN USAGE:
## ========================================

1. Replace {taskId} with actual task IDs from your create responses
2. Make sure your application is running on http://localhost:8080
3. Test in sequence: Create tasks first, then test other features
4. Save successful task IDs for use in subsequent requests
5. Check response status codes and error messages
6. Use Postman Collections to organize these requests
7. Enable "Auto-follow redirects" in Postman settings
8. Set environment variables for base URL and common IDs

## ========================================
## ACTUATOR HEALTH ENDPOINT TROUBLESHOOTING:
## ========================================

If you get a 404 error for /actuator/health, follow these steps:

1. **Ensure Actuator Dependency is Added:**
   - Gradle: `implementation 'org.springframework.boot:spring-boot-starter-actuator'`
   - Maven: `<artifactId>spring-boot-starter-actuator</artifactId>`

2. **Check application.properties Configuration:**
   ```properties
   management.endpoints.web.exposure.include=health,info,metrics
   management.endpoint.health.show-details=always
   management.health.defaults.enabled=true
   ```

3. **Restart the Application:**
   ```bash
   ./gradlew bootRun
   ```

4. **Alternative Basic Health Check:**
   Use the custom health endpoint instead: `GET http://localhost:8080/health`

## ========================================
## EXPECTED RESPONSE CODES:
## ========================================

- GET requests: 200 OK
- POST create: 201 Created  
- PUT update: 200 OK
- DELETE: 204 No Content
- Not found: 404 Not Found
- Validation error: 400 Bad Request
- Server error: 500 Internal Server Error
