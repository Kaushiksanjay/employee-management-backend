package com.assessment.employees.repository;

import com.assessment.employees.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    List<Employee> findByFullNameContainingIgnoreCase(String name);
    @Query("""
        SELECT e FROM Employee e
        WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
          AND (:status = '' OR UPPER(CAST(e.status AS string)) = :status)
        """)
    Page<Employee> search(@Param("search") String search,
                          @Param("status") String status,
                          Pageable pageable);
}
