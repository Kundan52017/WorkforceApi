@echo off
echo.
echo 🚀 Workforce Management API - Demo Guide
echo ===============================================
echo.
echo This guide shows you how to test all implemented features
echo Base URL: http://localhost:5000
echo.
echo 📋 Prerequisites:
echo 1. Start the application: .\gradlew bootRun
echo 2. Wait for "Started WorkforcemgmtApplication" message
echo 3. Use Postman, curl, or VS Code REST Client extension
echo.
echo 🏠 1. WELCOME & HEALTH CHECK
echo ----------------------------
echo GET http://localhost:5000/                    ^(Welcome message^)
echo GET http://localhost:5000/health              ^(Health check^)
echo.
echo 👥 2. STAFF MANAGEMENT
echo ----------------------
echo GET http://localhost:5000/api/staff           ^(View pre-loaded staff^)
echo.
echo 📋 3. TASK CREATION
echo -------------------
echo POST http://localhost:5000/api/tasks
echo Content-Type: application/json
echo.
echo Body:
echo {
echo   "title": "Process Customer Order #123",
echo   "description": "Handle customer order and coordinate delivery", 
echo   "priority": "HIGH",
echo   "assignedStaffId": "staff-1",
echo   "startDate": "2025-08-02",
echo   "dueDate": "2025-08-05",
echo   "createdBy": "manager-1",
echo   "customerReference": "CUST-123"
echo }
echo.
echo 🐛 4. BUG FIX DEMONSTRATIONS
echo ----------------------------
echo POST http://localhost:5000/api/tasks/assign-by-ref?customerReference=CUST-123^&newStaffId=staff-2^&updatedBy=manager-1
echo ^(Bug 1 Fix: Reassignment cancels old task instead of duplicating^)
echo.
echo GET http://localhost:5000/api/tasks/date-range?startDate=2025-08-01^&endDate=2025-08-10
echo ^(Bug 2 Fix: Date range excludes cancelled tasks^)
echo.
echo ✨ 5. NEW FEATURE DEMONSTRATIONS
echo --------------------------------
echo GET http://localhost:5000/api/tasks/smart-daily?startDate=2025-08-02^&endDate=2025-08-02
echo ^(Feature 1: Smart daily view^)
echo.
echo PUT http://localhost:5000/api/tasks/{taskId}/priority
echo Body: {"priority": "LOW", "updatedBy": "manager-1"}
echo ^(Feature 2: Update priority^)
echo.
echo GET http://localhost:5000/api/tasks/priority/HIGH
echo ^(Feature 2: Get tasks by priority^)
echo.
echo POST http://localhost:5000/api/tasks/{taskId}/comments
echo Body: {"content": "Customer called to confirm delivery", "userId": "staff-1", "userName": "John Doe"}
echo ^(Feature 3: Add comment^)
echo.
echo GET http://localhost:5000/api/tasks/{taskId}
echo ^(Feature 3: Get task details with activity history and comments^)
echo.
echo 📊 6. ADDITIONAL TESTING
echo ------------------------
echo GET http://localhost:5000/api/tasks                              ^(All tasks^)
echo PUT http://localhost:5000/api/tasks/{taskId}/status?status=COMPLETED^&updatedBy=staff-1
echo.
echo ✅ TESTING COMPLETE!
echo.
echo 💡 Tips:
echo - Replace {taskId} with actual task ID from create response
echo - Use test-api.http file with VS Code REST Client for easier testing
echo - Check README.md for complete documentation
echo.
pause
