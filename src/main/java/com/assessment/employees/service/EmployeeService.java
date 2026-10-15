package com.assessment.employees.service;

import com.assessment.employees.entity.Employee;
import com.assessment.employees.exception.DuplicateEmailException;
import com.assessment.employees.exception.ResourceNotFoundException;
import com.assessment.employees.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) { this.repository = repository; }

    public Page<Employee> getAll(int page, int size, String search, String status) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by("id").ascending());

        String term = search == null ? "" : search.trim();
        String statusFilter = status == null ? "" : status.trim().toUpperCase();

        return repository.search(term, statusFilter, pageable);
    }

    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee create(Employee employee) {
        if (repository.existsByEmailIgnoreCase(employee.getEmail().trim())) {
            throw new DuplicateEmailException("An employee with this email already exists");
        }
        employee.setId(null);
        employee.setEmail(employee.getEmail().trim().toLowerCase());
        return repository.save(employee);
    }

    public Employee update(Long id, Employee input) {
        Employee existing = getById(id);
        if (repository.existsByEmailIgnoreCaseAndIdNot(input.getEmail().trim(), id)) {
            throw new DuplicateEmailException("An employee with this email already exists");
        }
        existing.setFullName(input.getFullName().trim());
        existing.setEmail(input.getEmail().trim().toLowerCase());
        existing.setDepartment(input.getDepartment().trim());
        existing.setDesignation(input.getDesignation().trim());
        existing.setStatus(input.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Employee existing = getById(id);
        repository.delete(existing);
    }
}