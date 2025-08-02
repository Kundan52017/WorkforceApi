package com.workforcemgmt.service;

import com.workforcemgmt.exception.ResourceNotFoundException;
import com.workforcemgmt.model.Staff;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing staff members
 */
@Service
public class StaffService {
    private final Map<String, Staff> staffStorage = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeData() {
        // Initialize with some sample staff members
        createStaff(new Staff("staff-1", "John Doe", "john.doe@company.com", "Sales"));
        createStaff(new Staff("staff-2", "Jane Smith", "jane.smith@company.com", "Operations"));
        createStaff(new Staff("staff-3", "Mike Johnson", "mike.johnson@company.com", "Sales"));
        createStaff(new Staff("staff-4", "Sarah Wilson", "sarah.wilson@company.com", "Operations"));
    }

    public Staff createStaff(Staff staff) {
        if (staff.getId() == null || staff.getId().isEmpty()) {
            staff.setId(UUID.randomUUID().toString());
        }
        staffStorage.put(staff.getId(), staff);
        return staff;
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffStorage.values());
    }

    public Staff getStaffById(String id) {
        Staff staff = staffStorage.get(id);
        if (staff == null) {
            throw new ResourceNotFoundException("Staff member not found with id: " + id);
        }
        return staff;
    }

    public Staff updateStaff(String id, Staff updatedStaff) {
        if (!staffStorage.containsKey(id)) {
            throw new ResourceNotFoundException("Staff member not found with id: " + id);
        }
        updatedStaff.setId(id);
        staffStorage.put(id, updatedStaff);
        return updatedStaff;
    }

    public void deleteStaff(String id) {
        if (!staffStorage.containsKey(id)) {
            throw new ResourceNotFoundException("Staff member not found with id: " + id);
        }
        staffStorage.remove(id);
    }

    public boolean staffExists(String id) {
        return staffStorage.containsKey(id);
    }
}
