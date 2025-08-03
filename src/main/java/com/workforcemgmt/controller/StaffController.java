package com.workforcemgmt.controller;

import com.workforcemgmt.dto.StaffDto;
import com.workforcemgmt.model.Staff;
import com.workforcemgmt.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Staff Management", description = "APIs for managing workforce staff members and their information")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @Operation(summary = "Get all staff members", 
               description = "Retrieves a list of all staff members in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Staff list retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StaffDto.class)))
    })
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<Staff> staff = staffService.getAllStaff();
        List<StaffDto> staffDtos = staff.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(staffDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get staff member by ID", 
               description = "Retrieves detailed information for a specific staff member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Staff member retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StaffDto.class))),
        @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public ResponseEntity<StaffDto> getStaffById(
            @Parameter(description = "Staff member ID", required = true, example = "staff-1")
            @PathVariable String id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(convertToDto(staff));
    }

    @PostMapping
    @Operation(summary = "Create new staff member", 
               description = "Creates a new staff member in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Staff member created successfully",
                    content = @Content(schema = @Schema(implementation = StaffDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid staff data")
    })
    public ResponseEntity<StaffDto> createStaff(
            @Parameter(description = "Staff member data", required = true)
            @Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdStaff));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update staff member", 
               description = "Updates an existing staff member's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Staff member updated successfully",
                    content = @Content(schema = @Schema(implementation = StaffDto.class))),
        @ApiResponse(responseCode = "404", description = "Staff member not found"),
        @ApiResponse(responseCode = "400", description = "Invalid staff data")
    })
    public ResponseEntity<StaffDto> updateStaff(
            @Parameter(description = "Staff member ID", required = true, example = "staff-1")
            @PathVariable String id, 
            @Parameter(description = "Updated staff member data", required = true)
            @Valid @RequestBody StaffDto staffDto) {
        Staff staff = convertToEntity(staffDto);
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ResponseEntity.ok(convertToDto(updatedStaff));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete staff member", 
               description = "Removes a staff member from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Staff member deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public ResponseEntity<Void> deleteStaff(
            @Parameter(description = "Staff member ID", required = true, example = "staff-1")
            @PathVariable String id) {
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
