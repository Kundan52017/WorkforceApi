package com.workforcemgmt.controller;

import com.workforcemgmt.dto.StaffDto;
import com.workforcemgmt.model.Staff;
import com.workforcemgmt.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for managing staff members
 */
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<Staff> staff = staffService.getAllStaff();
        List<StaffDto> staffDtos = staff.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(staffDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable String id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(convertToDto(staff));
    }

    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdStaff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable String id, @Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ResponseEntity.ok(convertToDto(updatedStaff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    private StaffDto convertToDto(Staff staff) {
        return new StaffDto(staff.getId(), staff.getName(), staff.getEmail(), staff.getDepartment());
    }

    private Staff convertToEntity(StaffDto staffDto) {
        return new Staff(staffDto.getId(), staffDto.getName(), staffDto.getEmail(), staffDto.getDepartment());
    }
}
