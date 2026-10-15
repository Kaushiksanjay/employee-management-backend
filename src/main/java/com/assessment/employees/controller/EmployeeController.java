package com.assessment.employees.controller;

import com.assessment.employees.dto.ApiResponse;
import com.assessment.employees.entity.Employee;
import com.assessment.employees.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(service.getAll(page, size, search, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> create(@Valid @RequestBody Employee employee) {
        Employee created = service.create(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("Employee created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(
            @PathVariable Long id, @Valid @RequestBody Employee employee) {
        Employee updated = service.update(id, employee);
        return ResponseEntity.ok(ApiResponse.of("Employee updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.of("Employee with id " + id + " deleted successfully"));
    }
}