#!/bin/bash

# Workforce Management API Test Script
# This script demonstrates all the implemented features

echo "🚀 Starting Workforce Management API Demo..."
echo ""

BASE_URL="http://localhost:5000"

# Function to make HTTP requests and display results
test_endpoint() {
    echo "📡 Testing: $1"
    echo "🔗 URL: $2"
    echo "📄 Method: $3"
    if [ ! -z "$4" ]; then
        echo "📝 Body: $4"
    fi
    echo "---"
    echo ""
}

echo "🏠 1. Welcome & Health Check"
test_endpoint "Welcome Message" "GET $BASE_URL/" "GET"
test_endpoint "Health Check" "GET $BASE_URL/health" "GET"

echo "👥 2. Staff Management"
test_endpoint "Get All Staff" "GET $BASE_URL/api/staff" "GET"

echo "📋 3. Task Creation"
TASK_BODY='{
  "title": "Process Customer Order #123",
  "description": "Handle customer order and coordinate delivery",
  "priority": "HIGH",
  "assignedStaffId": "staff-1",
  "startDate": "2025-08-02",
  "dueDate": "2025-08-05",
  "createdBy": "manager-1",
  "customerReference": "CUST-123"
}'
test_endpoint "Create New Task" "POST $BASE_URL/api/tasks" "POST" "$TASK_BODY"

echo "🐛 4. Bug Fix Demonstrations"
test_endpoint "Bug 1 Fix - Reassign Task (no duplicates)" "POST $BASE_URL/api/tasks/assign-by-ref?customerReference=CUST-123&newStaffId=staff-2&updatedBy=manager-1" "POST"
test_endpoint "Bug 2 Fix - Date Range (excludes cancelled)" "GET $BASE_URL/api/tasks/date-range?startDate=2025-08-01&endDate=2025-08-10" "GET"

echo "✨ 5. New Feature Demonstrations"
test_endpoint "Feature 1 - Smart Daily View" "GET $BASE_URL/api/tasks/smart-daily?startDate=2025-08-02&endDate=2025-08-02" "GET"

PRIORITY_BODY='{"priority": "LOW", "updatedBy": "manager-1"}'
test_endpoint "Feature 2 - Update Priority" "PUT $BASE_URL/api/tasks/{taskId}/priority" "PUT" "$PRIORITY_BODY"
test_endpoint "Feature 2 - Get Tasks by Priority" "GET $BASE_URL/api/tasks/priority/HIGH" "GET"

COMMENT_BODY='{"content": "Customer called to confirm delivery address", "userId": "staff-1", "userName": "John Doe"}'
test_endpoint "Feature 3 - Add Comment" "POST $BASE_URL/api/tasks/{taskId}/comments" "POST" "$COMMENT_BODY"
test_endpoint "Feature 3 - Get Task Details (with history)" "GET $BASE_URL/api/tasks/{taskId}" "GET"

echo "📊 6. Additional Operations"
test_endpoint "Get All Tasks" "GET $BASE_URL/api/tasks" "GET"
test_endpoint "Update Task Status" "PUT $BASE_URL/api/tasks/{taskId}/status?status=COMPLETED&updatedBy=staff-1" "PUT"

echo ""
echo "✅ Demo script complete!"
echo "💡 Use the test-api.http file with VS Code REST Client extension for interactive testing"
echo "📚 See README.md for complete API documentation"
