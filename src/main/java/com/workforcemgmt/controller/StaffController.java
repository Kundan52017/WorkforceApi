package com.workforcemgmt.controller;

import com.workforcemgmt.dto.StaffDto;
import com.workforcemgmt.model.Staff;
import com.workforcemgmt.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff Management", description = "APIs for managing workforce staff members and their information")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @Operation(summary = "Get all staff members")
    @ApiResponse(responseCode = "200", description = "Staff list retrieved successfully")
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<Staff> staff = staffService.getAllStaff();
        List<StaffDto> staffDtos = staff.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(staffDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get staff member by ID")
    @ApiResponse(responseCode = "200", description = "Staff member retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Staff member not found")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable String id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(convertToDto(staff));
    }

    @PostMapping
    @Operation(summary = "Create new staff member")
    @ApiResponse(responseCode = "201", description = "Staff member created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid staff data")
    public ResponseEntity<StaffDto> createStaff(@Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdStaff));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update staff member")
    @ApiResponse(responseCode = "200", description = "Staff member updated successfully")
    @ApiResponse(responseCode = "404", description = "Staff member not found")
    @ApiResponse(responseCode = "400", description = "Invalid staff data")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable String id, @Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ResponseEntity.ok(convertToDto(updatedStaff));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete staff member")
    @ApiResponse(responseCode = "204", description = "Staff member deleted successfully")
    @ApiResponse(responseCode = "404", description = "Staff member not found")
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
