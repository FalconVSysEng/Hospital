package com.hospital.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.auth_service.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByDni(String dni);
}
